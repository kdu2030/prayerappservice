CREATE SEQUENCE IF NOT EXISTS prayer_session_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS prayer_session (
    prayer_session_id INT PRIMARY KEY NOT NULL DEFAULT nextval('prayer_session_seq'),
    user_id INT NOT NULL,
    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user(user_id),
    CHECK (end_time >= start_time)
);