CREATE OR REPLACE PROCEDURE delete_prayer_request (
    p_prayer_request_id INT
)
AS
$$
BEGIN
    DELETE FROM
        prayer_request_session prs
    WHERE
        prs.prayer_request_id = p_prayer_request_id;

    DELETE FROM
        prayer_request_like prl
    WHERE
        prl.prayer_request_id = p_prayer_request_id;

    DELETE FROM
        prayer_request_comment prc
    WHERE
        prc.prayer_request_id = p_prayer_request_id;

    DELETE FROM
        prayer_request_bookmark prb
    WHERE
        prb.prayer_request_id = p_prayer_request_id;

    DELETE FROM
        prayer_request pr
    WHERE
        pr.prayer_request_id = p_prayer_request_id;
END;
$$
LANGUAGE plpgsql;