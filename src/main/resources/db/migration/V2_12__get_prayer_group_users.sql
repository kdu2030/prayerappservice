CREATE OR REPLACE FUNCTION get_prayer_group_users(group_id INT, prayer_group_roles VARCHAR(255)[] DEFAULT NULL)
RETURNS TABLE (
    user_id INT,
    full_name VARCHAR(255),
    username VARCHAR(255),
    prayer_group_role VARCHAR(255),
    image_file_id INT,
    file_name VARCHAR(255),
    file_url VARCHAR(255),
    file_type VARCHAR(255)
)
AS
$$
BEGIN
    RETURN QUERY
    SELECT
        a.user_id,
        a.full_name,
        a.username,
        g.prayer_group_role,
        a.image_file_id,
        f.file_name,
        f.file_url,
        f.file_type
    FROM
        prayer_group_user g
    INNER JOIN
        app_user a ON g.user_id = a.user_id
    LEFT JOIN
        media_file f ON f.media_file_id = a.image_file_id
    WHERE
        prayer_group_id = group_id AND (prayer_group_roles IS NULL OR g.prayer_group_role = ANY(prayer_group_roles));
    RETURN;
END;
$$
LANGUAGE plpgsql;