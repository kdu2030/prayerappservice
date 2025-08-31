CREATE SEQUENCE join_request_seq
START 1
INCREMENT 50;

CREATE TABLE IF EXISTS join_request (
    join_request_id INT PRIMARY KEY DEFAULT nextval('join_request_seq'),
    user_id INT NOT NULL,
    prayer_group_id INT NOT NULL,
    submitted_date DATE NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user(user_id),
    CONSTRAINT fk_prayer_group_id FOREIGN KEY (prayer_group_id) REFERENCES prayer_group(prayer_group_id)
);