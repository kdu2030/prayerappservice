ALTER TABLE join_request
ALTER COLUMN submitted_date
TYPE TIMESTAMPTZ
USING submitted_date::timestamptz;