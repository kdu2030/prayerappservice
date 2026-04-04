CREATE OR REPLACE FUNCTION get_prayer_request (
    p_prayer_request_id INT,
    p_user_id INT
) RETURNS TABLE (
    prayer_request_id INT,
    request_title VARCHAR(255),
    request_description TEXT,
    created_date TIMESTAMPTZ,
    expiration_date TIMESTAMPTZ,
    prayer_group_id INT,
    group_name VARCHAR(255),
    avatar_file_id INT,
    avatar_file_name VARCHAR(255),
    avatar_file_type VARCHAR(255),
    avatar_file_url VARCHAR(255),
    user_id INT,
    username VARCHAR(255),
    full_name VARCHAR(255),
    user_file_id INT,
    user_file_name VARCHAR(255),
    user_file_type VARCHAR(255),
    user_file_url VARCHAR(255),
    like_count INT,
    comment_count INT,
    prayed_count INT,
    user_like_id INT,
    user_comment_id INT,
    user_bookmark_id INT,
    user_prayer_session_id INT
)
AS
$$
BEGIN
DROP TABLE IF EXISTS temp_prayer_request;
DROP TABLE IF EXISTS temp_prayer_group;

CREATE TEMPORARY TABLE temp_prayer_request (
        prayer_request_id INT,
        request_title VARCHAR(255),
        request_description TEXT,
        created_date TIMESTAMPTZ,
        expiration_date TIMESTAMPTZ,
        prayer_group_id INT,
        user_id INT,
        like_count INT,
        comment_count INT,
        prayed_count INT
    );

    CREATE TEMPORARY TABLE temp_prayer_group (
        prayer_group_id INT,
        group_name VARCHAR(255),
        avatar_file_id INT,
        visibility_level VARCHAR(255)
    );

INSERT INTO
    temp_prayer_request (prayer_request_id, request_title, request_description, created_date, expiration_date, prayer_group_id, user_id, like_count, comment_count, prayed_count)
SELECT
    r.prayer_request_id,
    r.request_title,
    r.request_description,
    r.created_date,
    r.expiration_date,
    r.prayer_group_id,
    r.user_id,
    r.like_count,
    r.comment_count,
    r.prayed_count
FROM
    prayer_request r
WHERE
    r.prayer_request_id = p_prayer_request_id;

INSERT INTO
    temp_prayer_group (prayer_group_id, group_name, avatar_file_id, visibility_level)
SELECT
    g.prayer_group_id,
    g.group_name,
    g.avatar_file_id,
    g.visibility_level
FROM
    prayer_group g
        INNER JOIN temp_prayer_request r ON r.prayer_group_id = g.prayer_group_id;

IF EXISTS (
        SELECT
            1
        FROM
            temp_prayer_group g
        LEFT JOIN
            prayer_group_user pgu ON pgu.prayer_group_id = g.prayer_group_id AND pgu.user_id = p_user_id
        WHERE
            visibility_level = 'PRIVATE' AND pgu.prayer_group_user_id IS NULL
    )
    THEN
        RAISE EXCEPTION 'Cannot view prayer requests from private prayer groups without membership.';
END IF;

RETURN QUERY
SELECT
    pr.prayer_request_id,
    pr.request_title,
    pr.request_description,
    pr.created_date,
    pr.expiration_date,
    g.prayer_group_id,
    g.group_name,
    g.avatar_file_id,
    gf.file_name AS avatar_file_name,
    gf.file_type AS avatar_file_type,
    gf.file_url AS avatar_file_url,
    u.user_id,
    u.username,
    u.full_name,
    u.image_file_id AS user_file_id,
    uf.file_name AS user_file_name,
    uf.file_type AS user_file_type,
    uf.file_url AS user_file_url,
    pr.like_count,
    pr.comment_count,
    pr.prayed_count,
    l.prayer_request_like_id,
    c.prayer_request_comment_id,
    b.prayer_request_bookmark_id,
    ps.prayer_session_id
FROM
    temp_prayer_request pr
        INNER JOIN
    temp_prayer_group g ON pr.prayer_group_id = g.prayer_group_id
        LEFT JOIN
    media_file gf ON gf.media_file_id = g.avatar_file_id
        LEFT JOIN
    app_user u ON u.user_id = pr.user_id
        LEFT JOIN
    media_file uf ON uf.media_file_id = u.image_file_id
        LEFT JOIN
    prayer_request_like l ON l.prayer_request_id = pr.prayer_request_id AND l.user_id = p_user_id
        LEFT JOIN
    prayer_request_comment c ON c.prayer_request_id = pr.prayer_request_id AND l.user_id = p_user_id
        LEFT JOIN
    prayer_request_bookmark b ON b.prayer_request_id = pr.prayer_request_id AND b.user_id = p_user_id
        LEFT JOIN
    prayer_request_session prs ON prs.prayer_request_id = pr.prayer_request_id
        LEFT JOIN
    prayer_session ps ON ps.prayer_session_id = prs.prayer_session_id AND ps.user_id = p_user_id;



DROP TABLE IF EXISTS temp_prayer_request;
DROP TABLE IF EXISTS temp_prayer_group;
EXCEPTION
    WHEN OTHERS THEN
DROP TABLE IF EXISTS temp_prayer_request;
DROP TABLE IF EXISTS temp_prayer_group;
RAISE;
END;
$$
LANGUAGE plpgsql;
