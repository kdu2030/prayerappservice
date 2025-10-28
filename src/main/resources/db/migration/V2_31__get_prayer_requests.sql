CREATE OR REPLACE FUNCTION get_prayer_requests (
    p_target_user_id INT,
    p_prayer_group_ids INT[],
    p_creator_user_ids INT[],
    p_bookmarked_user_id INT,
    p_include_expired BOOLEAN,
    p_sort_field VARCHAR(255),
    p_sort_direction VARCHAR(255),
    p_skip INT,
    p_take INT
) RETURNS TABLE (
    prayer_request_id INT,
    request_title VARCHAR(255),
    request_description TEXT,
    created_date TIMESTAMPTZ,
    expiration_date TIMESTAMPTZ,
    like_count INT,
    comment_count INT,
    prayed_count INT,
    user_like_id INT,
    user_comment_id INT,
    user_bookmark_id INT,
    user_prayer_session_id INT,
    prayer_group_id INT,
    group_name VARCHAR(255),
    avatar_file_id INT,
    avatar_file_name VARCHAR(255),
    avatar_file_url VARCHAR(255),
    avatar_file_type VARCHAR(255),
    user_id INT,
    username VARCHAR(255),
    full_name VARCHAR(255),
    user_file_id INT,
    user_file_name VARCHAR(255),
    user_file_url VARCHAR(255),
    user_file_type VARCHAR(255)
)
AS
$$
BEGIN
    RETURN QUERY
        WITH user_prayer_request_sessions AS (
            SELECT
                prs.prayer_request_id,
                s.prayer_session_id
            FROM
                prayer_session s
            INNER JOIN
                prayer_request_session prs ON prs.prayer_session_id = s.prayer_session_id
            WHERE
                s.user_id = p_target_user_id
        )

        SELECT
            r.prayer_request_id,
            r.request_title,
            r.request_description,
            r.created_date,
            r.expiration_date,
            r.like_count,
            r.comment_count,
            r.prayed_count,
            l.prayer_request_like_id,
            c.prayer_request_comment_id,
            b.prayer_request_bookmark_id,
            uprs.prayer_session_id,
            g.prayer_group_id,
            g.group_name,
            f1.media_file_id,
            f1.file_name,
            f1.file_url,
            f1.file_type,
            u.user_id,
            u.username,
            u.full_name,
            f2.media_file_id,
            f2.file_name,
            f2.file_url,
            f2.file_type
        FROM
            prayer_request r
        INNER JOIN
            prayer_group g ON g.prayer_group_id = r.prayer_group_id
        INNER JOIN
            app_user u ON u.user_id = r.user_id
        LEFT JOIN
            prayer_request_like l ON l.prayer_request_id = r.prayer_request_id AND l.user_id = p_target_user_id
        LEFT JOIN
            prayer_request_comment c ON c.prayer_request_id = r.prayer_request_id AND c.user_id = p_target_user_id
        LEFT JOIN
            prayer_request_bookmark b ON b.prayer_request_id = r.prayer_request_id
        LEFT JOIN
            user_prayer_request_sessions uprs ON uprs.prayer_request_id = r.prayer_request_id
        LEFT JOIN
            media_file f1 ON f1.media_file_id = g.avatar_file_id
        LEFT JOIN
            media_file f2 ON f2.media_file_id = u.image_file_id
        WHERE
            (p_prayer_group_ids IS NULL OR r.prayer_group_id = ANY(p_prayer_group_ids))
            AND (p_creator_user_ids IS NULL OR r.user_id = ANY(p_creator_user_ids))
            AND (p_bookmarked_user_id IS NULL OR b.user_id = p_bookmarked_user_id)
            AND (p_include_expired IS TRUE OR r.expiration_date IS NULL OR r.expiration_date > CURRENT_TIMESTAMP)
        ORDER BY
            CASE WHEN p_sort_field = 'CREATED_DATE' AND p_sort_direction = 'ASCENDING' THEN r.created_date END ASC,
            CASE WHEN p_sort_field = 'CREATED_DATE' AND p_sort_direction = 'DESCENDING' THEN r.created_date END DESC,
            CASE WHEN p_sort_field = 'LIKE_COUNT' AND p_sort_direction = 'ASCENDING' THEN r.like_count END ASC,
            CASE WHEN p_sort_field = 'LIKE_COUNT' AND p_sort_direction = 'DESCENDING' THEN r.like_count END DESC,
            CASE WHEN p_sort_field = 'COMMENT_COUNT' AND p_sort_direction = 'ASCENDING' THEN r.comment_count END ASC,
            CASE WHEN p_sort_field = 'COMMENT_COUNT' AND p_sort_direction = 'DESCENDING' THEN r.comment_count END DESC,
            CASE WHEN p_sort_field = 'PRAYED_COUNT' AND p_sort_direction = 'ASCENDING' THEN r.prayed_count END ASC,
            CASE WHEN p_sort_field = 'PRAYED_COUNT' AND p_sort_direction = 'DESCENDING' THEN r.prayed_count END DESC
        OFFSET
            p_skip
        LIMIT
            p_take;
END;
$$
LANGUAGE plpgsql;