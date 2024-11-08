BEGIN TRANSACTION;

CREATE TABLE IF NOT EXISTS file_type (
    file_type_id INT PRIMARY KEY,
    file_type_desc VARCHAR(255)
);

INSERT INTO
    file_type (file_type_id, file_type_desc)
VALUES (1, 'image');


CREATE SEQUENCE IF NOT EXISTS file_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS file (
    file_id INT PRIMARY KEY DEFAULT nextval('file_seq'),
    file_name VARCHAR(255) NOT NULL,
    file_url VARCHAR(255) NOT NULL UNIQUE,
    file_type_id INT,
    CONSTRAINT fk_file_type_id FOREIGN KEY (file_type_id) REFERENCES file_type(file_type_id)
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

CREATE TABLE IF NOT EXISTS prayer_group_role (
    prayer_group_role_id INT PRIMARY KEY,
    prayer_group_role_desc VARCHAR(255) NOT NULL
);

INSERT INTO prayer_group_role (prayer_group_role_id, prayer_group_role_desc) VALUES (1, 'admin');
INSERT INTO prayer_group_role (prayer_group_role_id, prayer_group_role_desc) VALUES (2, 'user');

CREATE SEQUENCE IF NOT EXISTS prayer_group_user_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS prayer_group_user (
    prayer_group_user_id INT PRIMARY KEY DEFAULT nextval('prayer_group_user_seq'),
    prayer_group_id INT NOT NULL,
    user_id INT NOT NULL,
    prayer_group_role_id INT NOT NULL,
    CONSTRAINT fk_prayer_group_id FOREIGN KEY (prayer_group_id) REFERENCES prayer_group(prayer_group_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user(user_id),
    CONSTRAINT fk_prayer_group_role_id FOREIGN KEY (prayer_group_role_id) REFERENCES prayer_group_role (prayer_group_role_id)
);

COMMIT;