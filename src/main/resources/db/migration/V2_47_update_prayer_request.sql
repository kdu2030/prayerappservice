CREATE OR REPLACE FUNCTION update_prayer_request (
    p_user_id INT,
    p_prayer_request_id INT,
    p_request_title VARCHAR(255),
    p_request_description TEXT,
    p_expiration_date TIMESTAMPTZ
)
RETURNS TABLE (
    prayer_request_id INT,
    request_title VARCHAR(255),
    request_description TEXT,
    created_date TIMESTAMPTZ,
    expiration_date TIMESTAMPTZ,
    prayer_group_id INT,
    group_name VARCHAR(255),
    avatar_file_id INT,
    avatar_file_name VARCHAR(255),
    avatar_file_type VARCHAR(255),
    avatar_file_url VARCHAR(255),
    user_id INT,
    username VARCHAR(255),
    full_name VARCHAR(255),
    user_file_id INT,
    user_file_name VARCHAR(255),
    user_file_type VARCHAR(255),
    user_file_url VARCHAR(255),
    like_count INT,
    comment_count INT,
    prayed_count INT,
    user_like_id INT,
    user_bookmark_id INT
)
AS
$$
BEGIN
    DROP TABLE IF EXISTS temp_prayer_request;

    CREATE TEMPORARY TABLE temp_prayer_request (
        prayer_request_id INT,
        request_title VARCHAR(255),
        request_description TEXT,
        created_date TIMESTAMPTZ,
        expiration_date TIMESTAMPTZ,
        prayer_group_id INT,
        user_id INT,
        like_count INT,
        comment_count INT,
        prayed_count INT
    );

    INSERT INTO temp_prayer_request (
        prayer_request_id,
        request_title,
        request_description,
        created_date,
        expiration_date,
        prayer_group_id,
        user_id,
        like_count,
        comment_count,
        prayed_count
    )
    SELECT
        pr.prayer_request_id,
        pr.request_title,
        pr.request_description,
        pr.created_date,
        pr.expiration_date,
        pr.prayer_group_id,
        pr.user_id,
        pr.like_count,
        pr.comment_count,
        pr.prayed_count
    FROM
        prayer_request pr
    WHERE
        pr.prayer_request_id = p_prayer_request_id;

    IF NOT EXISTS (
        SELECT
            1
        FROM
            temp_prayer_request tpr
        WHERE
            tpr.user_id = p_user_id
    )
    THEN
        RAISE EXCEPTION 'Only the submitted user can update the prayer request.';
    END IF;

    UPDATE
        prayer_request pr
    SET
        request_title = p_request_title,
        request_description = p_request_description,
        expiration_date = p_expiration_date
    WHERE
        pr.prayer_request_id = p_prayer_request_id;

    RETURN QUERY
        SELECT
            tpr.prayer_request_id,
            p_request_title,
            p_request_description,
            tpr.created_date,
            p_expiration_date,
            tpr.prayer_group_id,
            pg.group_name,
            pg.avatar_file_id,
            f1.file_name AS avatar_file_name,
            f1.file_type AS avatar_file_type,
            f1.file_url AS avatar_file_url,
            tpr.user_id,
            a.username,
            a.full_name,
            a.image_file_id AS user_file_id,
            f2.file_name AS user_file_name,
            f2.file_type AS user_file_type,
            f2.file_url AS user_file_url,
            tpr.like_count,
            tpr.comment_count,
            tpr.prayed_count,
            prl.prayer_request_like_id AS user_like_id,
            prb.prayer_request_bookmark_id AS user_bookmark_id
        FROM
            temp_prayer_request tpr
        INNER JOIN
            prayer_group pg ON pg.prayer_group_id = tpr.prayer_group_id
        INNER JOIN
            app_user a ON a.user_id = tpr.user_id
        LEFT JOIN
            media_file f1 ON f1.media_file_id = pg.avatar_file_id
        LEFT JOIN
            media_file f2 ON f2.media_file_id = a.image_file_id
        LEFT JOIN
            prayer_request_like prl ON prl.prayer_request_id = tpr.prayer_request_id AND prl.user_id = p_user_id
        LEFT JOIN
            prayer_request_bookmark prb ON prb.prayer_request_id = tpr.prayer_request_id AND prb.user_id = p_user_id;

    DROP TABLE IF EXISTS temp_prayer_request;
    RETURN;
EXCEPTION
    WHEN OTHERS THEN
        DROP TABLE IF EXISTS temp_prayer_request;
        RAISE;
END;
$$
LANGUAGE plpgsql;