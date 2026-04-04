CREATE OR REPLACE FUNCTION get_prayer_request_comments (
    p_prayer_request_id INT
) RETURNS TABLE (
    prayer_request_comment_id INT,
    submitted_date TIMESTAMPTZ,
    user_id INT,
    username VARCHAR(255),
    full_name VARCHAR(255),
    user_file_id INT,
    user_file_name VARCHAR(255),
    user_file_type VARCHAR(255),
    user_file_url VARCHAR(255)
)
AS
$$
BEGIN
RETURN QUERY
SELECT
    c.prayer_request_comment_id,
    c.submitted_date,
    u.user_id,
    u.username,
    u.full_name,
    u.image_file_id AS user_file_id,
    f.file_name AS user_file_name,
    f.file_type AS user_file_type,
    f.file_url AS user_file_url
FROM
    prayer_request_comment c
        INNER JOIN
    app_user u ON c.user_id = u.user_id
        LEFT JOIN
    media_file f ON f.media_file_id = u.image_file_id
WHERE
    prayer_request_id = p_prayer_request_id;

END;
$$
LANGUAGE plpgsql;
