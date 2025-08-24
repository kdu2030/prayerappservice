CREATE SEQUENCE IF NOT EXISTS prayer_group_user_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS prayer_group_user(
    prayer_group_user_id INT PRIMARY KEY DEFAULT nextval('prayer_group_user_seq'),
    user_id INT NOT NULL,
    prayer_group_id INT NOT NULL,
    prayer_group_role VARCHAR(255) NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user(user_id),
    CONSTRAINT fk_prayer_group_id FOREIGN KEY (prayer_group_id) REFERENCES prayer_group(prayer_group_id)
);
