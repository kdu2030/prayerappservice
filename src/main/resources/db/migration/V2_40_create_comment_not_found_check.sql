CREATE OR REPLACE FUNCTION add_prayer_request_comment(
    p_prayer_request_id INT,
    p_user_id INT,
    p_comment TEXT,
    p_submitted_date TIMESTAMPTZ
) RETURNS TABLE (
    prayer_request_comment_id INT,
    submitted_date TIMESTAMPTZ,
    comment TEXT,
    user_id INT,
    username VARCHAR(255),
    full_name VARCHAR(255),
    user_file_id INT,
    user_file_name VARCHAR(255),
    user_file_type VARCHAR(255),
    user_file_url VARCHAR(255)
)
AS
$$
DECLARE
    new_comment_id INT;
BEGIN
    DROP TABLE IF EXISTS temp_prayer_request;

    CREATE TEMPORARY TABLE temp_prayer_request (
            prayer_request_id INT,
            prayer_group_id INT
        );

    INSERT INTO
        temp_prayer_request (prayer_request_id, prayer_group_id)
    SELECT
        pr.prayer_request_id,
        pr.prayer_group_id
    FROM
        prayer_request pr
    WHERE
        pr.prayer_request_id = p_prayer_request_id;

    IF NOT EXISTS (
            SELECT
                1
            FROM
                temp_prayer_request
        )
        THEN
            RAISE EXCEPTION 'Cannot find prayer request';
    END IF;


    IF NOT EXISTS (
        SELECT
            1
        FROM
            prayer_group_user pgu
        INNER JOIN
            temp_prayer_request tpr ON pr.prayer_group_id = pgu.prayer_group_id
        WHERE
            tpr.prayer_request_id = p_prayer_request_id AND pgu.user_id = p_user_id
    )
    THEN
        RAISE EXCEPTION 'A user must be a member of the prayer group to comment on prayer requests.';
    END IF;

    INSERT INTO
        prayer_request_comment (comment, prayer_request_id, user_id, submitted_date)
    VALUES
        (p_comment, p_prayer_request_id, p_user_id, p_submitted_date)
        RETURNING
                prayer_request_comment.prayer_request_comment_id INTO new_comment_id;

    UPDATE
        prayer_request
    SET
        comment_count = comment_count + 1
    WHERE
        prayer_request_id = p_prayer_request_id;

    RETURN QUERY
    SELECT
        prc.prayer_request_comment_id,
        prc.submitted_date,
        prc.comment,
        au.user_id,
        au.username,
        au.full_name,
        au.image_file_id,
        f.file_name,
        f.file_type,
        f.file_url
    FROM
        prayer_request_comment prc
            INNER JOIN
        app_user au ON prc.user_id = au.user_id
            LEFT JOIN
        media_file f ON f.media_file_id = au.image_file_id
    WHERE
        prc.prayer_request_comment_id = new_comment_id;

    DROP TABLE IF EXISTS temp_prayer_request;
    RETURN;
EXCEPTION
        WHEN OTHERS THEN
            DROP TABLE IF EXISTS temp_prayer_request;
            RAISE;

END;
$$
LANGUAGE plpgsql;