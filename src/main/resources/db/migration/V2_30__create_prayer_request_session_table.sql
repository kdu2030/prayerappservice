CREATE SEQUENCE IF NOT EXISTS prayer_request_session_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS prayer_request_session (
    prayer_request_session_id INT NOT NULL DEFAULT nextval('prayer_request_session_seq'),
    prayer_request_id INT NOT NULL,
    prayer_session_id INT NOT NULL,
    CONSTRAINT fk_prayer_request_id FOREIGN KEY (prayer_request_id) REFERENCES prayer_request(prayer_request_id),
    CONSTRAINT fk_prayer_session_id FOREIGN KEY (prayer_session_id) REFERENCES prayer_session(prayer_session_id)
);