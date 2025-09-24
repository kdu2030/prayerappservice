CREATE OR REPLACE PROCEDURE delete_join_requests(target_prayer_group_id INT, join_request_ids INT[])
AS
$$
BEGIN
    DELETE FROM
        join_request j
    WHERE
        j.prayer_group_id = target_prayer_group_id AND j.join_request_id = ANY(join_request_ids);
END;
$$
LANGUAGE plpgsql;
