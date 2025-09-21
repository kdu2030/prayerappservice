CREATE INDEX IF NOT EXISTS group_name_trgm_idx
ON prayer_group
USING gin (group_name gin_trgm_ops);