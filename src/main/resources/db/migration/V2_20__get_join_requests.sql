CREATE OR REPLACE FUNCTION get_join_requests (
    target_prayer_group_id INT
) RETURNS TABLE (
    join_request_id INT,
    prayer_group_id INT,
    submitted_date TIMESTAMPTZ,
    user_id INT,
    full_name VARCHAR(255),
    username VARCHAR(255),
    media_file_id INT,
    file_name VARCHAR(255),
    file_url VARCHAR(255),
    file_type VARCHAR(255)
)
AS
$$
BEGIN
    RETURN QUERY
        SELECT
            j.join_request_id,
            j.prayer_group_id,
            j.submitted_date,
            j.user_id,
            u.full_name,
            u.username,
            f.media_file_id,
            f.file_name,
            f.file_url,
            f.file_type
        FROM
            join_request j
        INNER JOIN
            app_user u ON u.user_id = j.user_id
        LEFT JOIN
            media_file f ON f.media_file_id = u.image_file_id
        WHERE
            j.prayer_group_id = target_prayer_group_id;
END;
$$
LANGUAGE plpgsql;