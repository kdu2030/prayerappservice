CREATE OR REPLACE FUNCTION search_prayer_groups (
    group_name_query VARCHAR(255)
) RETURNS TABLE (
    prayer_group_id INT,
    group_name VARCHAR(255),
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
            g.prayer_group_id,
            g.group_name,
            f.media_file_id,
            f.file_name,
            f.file_url,
            f.file_type
        FROM
            prayer_group g
        INNER JOIN
            media_file f ON f.media_file_id = g.avatar_file_id
        WHERE
            g.group_name % group_name_query
        ORDER BY
            similarity(g.group_name, group_name_query) DESC;
END;
$$
LANGUAGE plpgsql;