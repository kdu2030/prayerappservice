ALTER TABLE prayer_group_user
ADD COLUMN join_date TIMESTAMPTZ;

UPDATE prayer_group_user
SET join_date = now()
WHERE join_date IS NULL;

ALTER TABLE prayer_group_user
ALTER COLUMN join_date SET NOT NULL;