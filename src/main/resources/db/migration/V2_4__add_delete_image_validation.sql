ALTER TABLE app_user
ADD COLUMN image_file_id INT;

ALTER TABLE app_user
ADD CONSTRAINT fk_image_file_id
FOREIGN KEY (image_file_id) REFERENCES file(file_id);

CREATE OR REPLACE FUNCTION validate_file_delete(file_to_delete_id INT)
RETURNS TABLE(can_delete BOOLEAN, delete_error TEXT) AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM file WHERE file_id = file_to_delete_id)
    THEN
        RETURN QUERY SELECT FALSE, 'File does not exist.';
        RETURN;
    END IF;

    IF EXISTS (SELECT 1 FROM prayer_group WHERE image_file_id = file_to_delete_id)
    THEN
        RETURN QUERY SELECT FALSE, 'File is associated with a prayer group.';
        RETURN;
    END IF;

    IF EXISTS (SELECT 1 FROM app_user WHERE image_file_id = file_to_delete_id)
    THEN
        RETURN QUERY SELECT FALSE, 'File is associated with a user.';
        RETURN;
    END IF;

    RETURN QUERY SELECT TRUE, NULL;
    RETURN;
END;
$$ LANGUAGE plpgsql;