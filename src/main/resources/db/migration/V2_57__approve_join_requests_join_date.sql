CREATE OR REPLACE PROCEDURE approve_join_requests(target_prayer_group_id INT, join_request_ids INT[], approve_date TIMESTAMPTZ)
AS
$$
BEGIN
    INSERT INTO
        prayer_group_user (user_id, prayer_group_id, prayer_group_role, join_date)
    SELECT
        user_id,
        prayer_group_id,
        'MEMBER',
        approve_date
    FROM
        join_request j
    WHERE
        j.prayer_group_id = target_prayer_group_id AND j.join_request_id = ANY(join_request_ids);

    DELETE FROM
        join_request j
    WHERE
        j.prayer_group_id = target_prayer_group_id AND j.join_request_id = ANY(join_request_ids);

END;
$$
LANGUAGE plpgsql;