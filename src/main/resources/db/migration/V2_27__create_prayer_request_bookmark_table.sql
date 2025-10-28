CREATE SEQUENCE IF NOT EXISTS prayer_request_bookmark_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS prayer_request_bookmark (
    prayer_request_bookmark_id INT PRIMARY KEY NOT NULL DEFAULT nextval('prayer_request_bookmark_seq'),
    prayer_request_id INT NOT NULL,
    user_id INT NOT NULL,
    submitted_date TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_prayer_request_id FOREIGN KEY (prayer_request_id) REFERENCES prayer_request(prayer_request_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user(user_id)
);