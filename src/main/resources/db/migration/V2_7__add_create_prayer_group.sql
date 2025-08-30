CREATE OR REPLACE FUNCTION create_prayer_group(
    creator_user_id INT,
    new_group_name VARCHAR(255),
    group_description TEXT,
    group_rules TEXT,
    group_visibility VARCHAR(255),
    group_avatar_file_id INT,
    group_banner_file_id INT
) RETURNS TABLE (
    prayer_group_id INT,
    group_name VARCHAR(255),
    description TEXT,
    rules TEXT,
    visibility_level VARCHAR(255),
    avatar_file_id INT,
    group_avatar_file_name VARCHAR(255),
    group_avatar_file_url VARCHAR(255),
    banner_file_id INT,
    group_banner_file_name VARCHAR(255),
    group_banner_file_url VARCHAR(255),
    admin_user_id INT,
    admin_full_name VARCHAR(255),
    admin_image_file_id INT,
    admin_image_file_name VARCHAR(255),
    admin_image_file_url VARCHAR(255)
)
AS
$$
DECLARE
        new_group_id INT;
BEGIN
    DROP TABLE IF EXISTS temp_admin_user;
    DROP TABLE IF EXISTS temp_relevant_files;

    CREATE TEMPORARY TABLE temp_admin_user (
        user_id INT,
        full_name VARCHAR(255),
        image_file_id INT
    );

    CREATE TEMPORARY TABLE temp_relevant_files (
        media_file_id INT,
        file_name VARCHAR(255),
        file_url VARCHAR(255),
        file_type VARCHAR(255)
    );

    INSERT INTO
        temp_admin_user (user_id, full_name, image_file_id)
    SELECT
        u.user_id,
        full_name,
        u.image_file_id
    FROM
        app_user u
    WHERE u.user_id = creator_user_id;

    INSERT INTO
        temp_relevant_files (media_file_id, file_name, file_url, file_type)
    SELECT
        f.media_file_id,
        f.file_name,
        f.file_url,
        f.file_type
    FROM media_file f, temp_admin_user a
    WHERE f.media_file_id IN (a.image_file_id, group_avatar_file_id, group_banner_file_id);

    IF NOT EXISTS (SELECT 1 FROM temp_admin_user)
    THEN
        RAISE EXCEPTION 'User does not exist.';
    END IF;

    IF group_avatar_file_id IS NOT NULL AND NOT EXISTS (
        SELECT 1 FROM temp_relevant_files f
        WHERE f.media_file_id = group_avatar_file_id AND file_type = 'IMAGE'
    )
    THEN
        RAISE EXCEPTION 'Avatar image for group not found or file is not an image.';
    END IF;

    IF group_banner_file_id IS NOT NULL AND NOT EXISTS (
        SELECT 1 FROM temp_relevant_files f
        WHERE f.media_file_id = group_banner_file_id AND file_type = 'IMAGE'
    )
    THEN
        RAISE EXCEPTION 'Banner image for group not found or file is not an image.';
    END IF;


    INSERT INTO
        prayer_group (group_name, description, rules, visibility_level, avatar_file_id, banner_file_id)
    VALUES
        (new_group_name, group_description, group_rules, group_visibility, group_avatar_file_id, group_banner_file_id)
    RETURNING prayer_group.prayer_group_id INTO new_group_id;

    INSERT INTO
        prayer_group_user (prayer_group_id, user_id, prayer_group_role)
    SELECT
        new_group_id,
        a.user_id,
        'ADMIN'
    FROM
        temp_admin_user a;

    RETURN QUERY
        SELECT
            new_group_id,
            new_group_name,
            group_description,
            group_rules,
            group_visibility,
            group_avatar_file_id,
            f2.file_name,
            f2.file_url,
            group_banner_file_id,
            f3.file_name,
            f3.file_url,
            a.user_id,
            a.full_name,
            a.image_file_id,
            f1.file_name,
            f1.file_url
        FROM temp_admin_user a
        LEFT JOIN temp_relevant_files f1 ON a.image_file_id = f1.media_file_id
        LEFT JOIN temp_relevant_files f2 ON f2.media_file_id = group_avatar_file_id
        LEFT JOIN temp_relevant_files f3 ON f3.media_file_id = group_banner_file_id;
    DROP TABLE IF EXISTS temp_admin_user;
    DROP TABLE IF EXISTS temp_relevant_files;
    RETURN;
EXCEPTION
    WHEN OTHERS THEN
        DROP TABLE IF EXISTS temp_admin_user;
        DROP TABLE IF EXISTS temp_relevant_files;
        RAISE;
END;
$$
LANGUAGE plpgsql;