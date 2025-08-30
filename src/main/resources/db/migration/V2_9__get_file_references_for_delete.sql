CREATE OR REPLACE FUNCTION get_file_references_for_delete(target_file_id INT)
RETURNS TABLE (
    entity_id INT,
    entity_type VARCHAR(255)
)
AS
$$
BEGIN
   RETURN QUERY
    SELECT
        user_id AS entity_id, 'USER'::VARCHAR(255) AS entity_type
    FROM
        app_user
    WHERE
        image_file_id = target_file_id
    UNION
    SELECT
        prayer_group_id AS entity_id, 'PRAYER_GROUP'::VARCHAR(255) AS entity_type
    FROM
        prayer_group
    WHERE
        avatar_file_id = target_file_id OR banner_file_id = target_file_id;

END;
$$
LANGUAGE plpgsql;