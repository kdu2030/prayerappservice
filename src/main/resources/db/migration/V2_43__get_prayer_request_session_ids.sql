
CREATE OR REPLACE FUNCTION get_user_prayer_session_ids(p_prayer_request_ids INT[], p_user_id INT)
RETURNS TABLE (
    prayer_request_id INT,
    prayer_session_id INT
)
AS
$$
BEGIN
RETURN QUERY
SELECT
    prs.prayer_request_id,
    prs.prayer_session_id
FROM
    prayer_request_session prs
        INNER JOIN
    prayer_session ps ON ps.prayer_session_id = prs.prayer_session_id
WHERE
    ps.user_id = p_user_id AND prs.prayer_request_id = ANY(p_prayer_request_ids);
END;
$$
LANGUAGE plpgsql;
