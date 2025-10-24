CREATE OR REPLACE FUNCTION create_prayer_request(
    p_request_title VARCHAR(255),
    p_request_description TEXT,
    p_created_date TIMESTAMPTZ,
    p_expiration_date TIMESTAMPTZ,
    p_prayer_group_id INT,
    p_user_id INT
) RETURNS TABLE (
    prayer_request_id INT,
    request_title VARCHAR(255),
    request_description TEXT,
    created_date TIMESTAMPTZ,
    expiration_date TIMESTAMPTZ,
    prayer_group_id INT,
    group_name VARCHAR(255),
    avatar_file_id INT,
    avatar_file_name VARCHAR(255),
    avatar_file_url VARCHAR(255),
    avatar_file_type VARCHAR(255),
    user_id INT,
    username VARCHAR(255),
    full_name VARCHAR(255),
    user_file_id INT,
    user_file_name VARCHAR(255),
    user_file_url VARCHAR(255),
    user_file_type VARCHAR(255)
)
AS
$$
DECLARE
    new_prayer_request_id INT;
BEGIN
    INSERT INTO
        prayer_request (request_title, request_description, created_date, expiration_date, prayer_group_id, user_id, like_count, comment_count, prayed_count)
    VALUES
        (p_request_title, p_request_description, p_created_date, p_expiration_date, p_prayer_group_id, p_user_id, 0, 0, 0)
    RETURNING prayer_request.prayer_request_id INTO new_prayer_request_id;

    RETURN QUERY
        SELECT
            new_prayer_request_id,
            p_request_title,
            p_request_description,
            p_created_date,
            p_expiration_date,
            p_prayer_group_id,
            g.group_name,
            g.avatar_file_id,
            f.file_name,
            f.file_url,
            f.file_type,
            p_user_id,
            u.username,
            u.full_name,
            u.image_file_id,
            f1.file_name,
            f1.file_url,
            f1.file_type
        FROM
            prayer_group g
        JOIN
            app_user u ON u.user_id = p_user_id
        LEFT JOIN
            media_file f ON f.media_file_id = g.avatar_file_id
        LEFT JOIN
            media_file f1 ON f1.media_file_id = u.image_file_id
        WHERE
            g.prayer_group_id = p_prayer_group_id;
END;
$$
LANGUAGE plpgsql;