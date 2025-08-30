ALTER TABLE app_user
ADD COLUMN image_file_id INT;

ALTER TABLE app_user
ADD CONSTRAINT fk_image_file_id
FOREIGN KEY (image_file_id) REFERENCES media_file(media_file_id);