DROP PROCEDURE IF EXISTS update_prayer_group_users;
DROP TYPE IF EXISTS prayer_group_user_update_item;

CREATE TYPE prayer_group_user_update_item AS (
    user_id INT,
    prayer_group_role VARCHAR(255)
);

CREATE OR REPLACE PROCEDURE update_prayer_group_users (p_prayer_group_id INT, p_updated_users prayer_group_user_update_item[])
AS $$
BEGIN
    DROP TABLE IF EXISTS prayer_group_update_items;

    CREATE TEMPORARY TABLE prayer_group_update_items (
        user_id INT,
        prayer_group_role VARCHAR(255)
    );

    INSERT INTO
        prayer_group_update_items (user_id, prayer_group_role)
    SELECT
        (u).user_id, (u).prayer_group_role
    FROM unnest(p_updated_users) AS u;

    DELETE FROM
        prayer_group_user pgu
    WHERE pgu.prayer_group_id = p_prayer_group_id AND pgu.user_id NOT IN (
        SELECT
            pgui.user_id
        FROM
            prayer_group_update_items pgui
    );

    MERGE INTO
        prayer_group_user AS pgu
    USING
        prayer_group_update_items AS pgui
    ON
        pgu.prayer_group_id = p_prayer_group_id AND pgu.user_id = pgui.user_id
    WHEN MATCHED THEN
        UPDATE SET prayer_group_role = pgui.prayer_group_role
    WHEN NOT MATCHED THEN
        INSERT (user_id, prayer_group_id, prayer_group_role) VALUES (pgui.user_id, p_prayer_group_id, pgui.prayer_group_role);

    EXCEPTION
        WHEN OTHERS THEN
            DROP TABLE IF EXISTS prayer_group_update_items;
            RAISE;

END;
$$
LANGUAGE plpgsql;