CREATE UNIQUE INDEX IF NOT EXISTS
    idx_join_request_user
ON
    join_request (user_id, prayer_group_id);