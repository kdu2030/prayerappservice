CREATE OR REPLACE FUNCTION get_prayer_requests_count (
    p_target_user_id INT,
    p_prayer_group_ids INT[],
    p_creator_user_ids INT[],
    p_bookmarked_user_id INT,
    p_include_expired BOOLEAN
) RETURNS TABLE (
    prayer_request_count BIGINT
)
AS
$$
BEGIN
    RETURN QUERY
        SELECT
            COUNT(r.prayer_request_id) AS prayer_request_count
        FROM
            prayer_request r
        LEFT JOIN
            prayer_request_bookmark b ON b.prayer_request_id = r.prayer_request_id
        WHERE
            (p_prayer_group_ids IS NULL OR r.prayer_group_id = ANY(p_prayer_group_ids))
            AND (p_creator_user_ids IS NULL OR r.user_id = ANY(p_creator_user_ids))
            AND (p_bookmarked_user_id IS NULL OR b.user_id = p_bookmarked_user_id)
            AND (p_include_expired IS TRUE OR r.expiration_date IS NULL OR r.expiration_date > CURRENT_TIMESTAMP);
END;
$$
LANGUAGE plpgsql;