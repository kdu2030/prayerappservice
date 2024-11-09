CREATE SEQUENCE IF NOT EXISTS file_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS file (
    file_id INT PRIMARY KEY DEFAULT nextval('file_seq'),
    file_name VARCHAR(255) NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    file_type VARCHAR(255) NOT NULL
);


CREATE SEQUENCE IF NOT EXISTS prayer_group_seq
START 1
INCREMENT 50;


CREATE TABLE IF NOT EXISTS prayer_group (
    prayer_group_id INT PRIMARY KEY DEFAULT nextval('prayer_group_seq'),
    group_name VARCHAR (255) NOT NULL UNIQUE,
    description TEXT,
    rules TEXT,
    color INT,
    image_file_id INT,
    CONSTRAINT fk_image_file_id FOREIGN KEY (image_file_id) REFERENCES file(file_id)
);

CREATE SEQUENCE IF NOT EXISTS prayer_group_user_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS prayer_group_user (
    prayer_group_user_id INT PRIMARY KEY DEFAULT nextval('prayer_group_user_seq'),
    prayer_group_id INT NOT NULL,
    user_id INT NOT NULL,
    prayer_group_role VARCHAR(255) NOT NULL,
    CONSTRAINT fk_prayer_group_id FOREIGN KEY (prayer_group_id) REFERENCES prayer_group (prayer_group_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user(user_id)
);