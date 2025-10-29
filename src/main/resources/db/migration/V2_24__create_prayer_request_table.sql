CREATE SEQUENCE IF NOT EXISTS prayer_request_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS prayer_request (
    prayer_request_id INT PRIMARY KEY DEFAULT nextval('prayer_request_seq'),
    request_title VARCHAR(255) NOT NULL,
    request_description TEXT NOT NULL,
    created_date TIMESTAMPTZ NOT NULL,
    like_count INT,
    comment_count INT,
    prayed_count INT,
    expiration_date TIMESTAMPTZ,
    prayer_group_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_prayer_group_id FOREIGN KEY (prayer_group_id) REFERENCES prayer_group(prayer_group_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user(user_id)
);