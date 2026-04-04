CREATE OR REPLACE PROCEDURE delete_prayer_request_comment (
    p_comment_id INT
)
AS
$$
DECLARE
    prayer_request_id_to_update INT;
BEGIN
    SELECT
        prayer_request_id
    INTO
        prayer_request_id_to_update
    FROM
        prayer_request_comment
    WHERE
        prayer_request_comment_id = p_comment_id;

    DELETE FROM
        prayer_request_comment
    WHERE
        prayer_request_comment_id = p_comment_id;

    UPDATE
        prayer_request
    SET
        comment_count = prayer_request.comment_count - 1
    WHERE
        prayer_request_id = prayer_request_id_to_update;
END;
$$
LANGUAGE plpgsql;