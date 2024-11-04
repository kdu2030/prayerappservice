BEGIN TRANSACTION;

ALTER TABLE app_user
ADD COLUMN email VARCHAR(255);

UPDATE app_user
SET email = user_email.email
FROM user_email
WHERE app_user.user_id = user_email.user_id;

DROP TABLE user_email;
DROP SEQUENCE user_email_seq;

COMMIT;

