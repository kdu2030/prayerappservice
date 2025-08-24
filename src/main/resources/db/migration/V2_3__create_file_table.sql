CREATE SEQUENCE IF NOT EXISTS media_file_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS media_file (
    media_file_id INT PRIMARY KEY DEFAULT nextval('media_file_seq'),
    file_name VARCHAR(255) NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    file_type VARCHAR(255) NOT NULL
);