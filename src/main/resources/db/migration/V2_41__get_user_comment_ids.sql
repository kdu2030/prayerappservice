CREATE OR REPLACE FUNCTION get_user_comment_ids (p_prayer_request_ids INT[], p_user_id INT)
RETURNS TABLE (
    prayer_request_id INT,
    prayer_request_comment_id INT
)
AS
$$
BEGIN
    RETURN QUERY
        SELECT
            c.prayer_request_id,
            c.prayer_request_comment_id
        FROM
            prayer_request_comment c
        WHERE
            c.user_id = p_user_id AND c.prayer_request_id = ANY(p_prayer_request_ids);

END;
$$
LANGUAGE plpgsql;