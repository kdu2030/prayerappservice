CREATE OR REPLACE FUNCTION get_prayer_group_summaries_by_user(
    p_user_id INT
) RETURNS TABLE (
    prayer_group_id INT,
    group_name VARCHAR(255),
    media_file_id INT,
    file_name VARCHAR(255),
    file_url VARCHAR(255),
    file_type VARCHAR(255),
    join_status VARCHAR(255)
)
AS
$$
BEGIN
    RETURN QUERY
        (
            SELECT
                g.prayer_group_id,
                g.group_name,
                f.media_file_id,
                f.file_name,
                f.file_url,
                f.file_type,
                'JOINED'::VARCHAR(255) AS join_status
            FROM
                prayer_group_user u
            INNER JOIN
                prayer_group g ON g.prayer_group_id = u.prayer_group_id
            LEFT JOIN
                media_file f ON f.media_file_id = g.avatar_file_id
            WHERE user_id = p_user_id
        )
        UNION (
            SELECT
                g.prayer_group_id,
                g.group_name,
                f.media_file_id,
                f.file_name,
                f.file_url,
                f.file_type,
                'REQUEST_SUBMITTED'::VARCHAR(255) AS join_status
            FROM
                join_request j
            INNER JOIN
                prayer_group g ON g.prayer_group_id = j.prayer_group_id
            LEFT JOIN
                media_file f ON f.media_file_id = g.avatar_file_id
            WHERE user_id = p_user_id
        );
END;
$$
LANGUAGE plpgsql;