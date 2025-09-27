DROP FUNCTION get_prayer_group;

CREATE OR REPLACE FUNCTION get_prayer_group(
    target_prayer_group_id INT,
    target_user_id INT
) RETURNS TABLE (
    prayer_group_id INT,
    group_name VARCHAR(255),
    description TEXT,
    rules TEXT,
    visibility_level VARCHAR(255),
    avatar_file_id INT,
    avatar_file_name VARCHAR(255),
    avatar_file_url VARCHAR(255),
    avatar_file_type VARCHAR(255),
    banner_file_id INT,
    banner_file_name VARCHAR(255),
    banner_file_url VARCHAR(255),
    banner_file_type VARCHAR(255),
    prayer_group_role VARCHAR(255),
    join_request_id INT,
    join_request_count INT
)
AS
$$
DECLARE
    join_request_count INT;
BEGIN
    SELECT
        COUNT(*)
    INTO
        join_request_count
    FROM
        join_request r1
    WHERE r1.prayer_group_id = target_prayer_group_id;

    RETURN QUERY
        SELECT
            g.prayer_group_id,
            g.group_name,
            g.description,
            g.rules,
            g.visibility_level,
            g.avatar_file_id,
            f1.file_name AS avatar_file_name,
            f1.file_url AS avatar_file_url,
            f1.file_type AS avatar_file_type,
            g.banner_file_id,
            f2.file_name AS banner_file_name,
            f2.file_url AS banner_file_url,
            f2.file_type AS banner_file_type,
            pgu.prayer_group_role,
            r2.join_request_id,
            join_request_count
        FROM
            prayer_group g
        LEFT JOIN
            media_file f1 ON f1.media_file_id = g.avatar_file_id
        LEFT JOIN
            media_file f2 ON f2.media_file_id = g.banner_file_id
        LEFT JOIN
            prayer_group_user pgu ON pgu.prayer_group_id = g.prayer_group_id AND pgu.user_id = target_user_id
        LEFT JOIN
            join_request r2 ON r2.prayer_group_id = g.prayer_group_id AND r2.user_id = target_user_id
        WHERE
            g.prayer_group_id = target_prayer_group_id;
END;
$$
LANGUAGE plpgsql;