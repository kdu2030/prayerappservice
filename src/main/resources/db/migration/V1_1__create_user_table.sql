CREATE SEQUENCE IF NOT EXISTS app_user_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS app_user(
    user_id INT PRIMARY KEY DEFAULT nextval('app_user_seq'),
    full_name VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR (255)
);

CREATE SEQUENCE IF NOT EXISTS user_email_seq
START 1
INCREMENT 50;

CREATE TABLE IF NOT EXISTS user_email(
    user_email_id INT PRIMARY KEY DEFAULT nextval('user_email_seq'),
    user_id INT UNIQUE,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES app_user(user_id) ON DELETE CASCADE
);


