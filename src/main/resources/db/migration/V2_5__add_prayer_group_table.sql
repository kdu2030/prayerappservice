CREATE SEQUENCE IF NOT EXISTS prayer_group_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS prayer_group(
    prayer_group_id INT PRIMARY KEY DEFAULT nextval('prayer_group_seq'),
    group_name VARCHAR(255) UNIQUE,
    description TEXT,
    rules TEXT,
    visibility_level VARCHAR(255),
    avatar_file_id INT,
    banner_file_id INT,
    CONSTRAINT fk_avatar_file_id FOREIGN KEY (avatar_file_id) REFERENCES media_file(media_file_id),
    CONSTRAINT fk_banner_file_id FOREIGN KEY (banner_file_id) REFERENCES media_file(media_file_id)
);
