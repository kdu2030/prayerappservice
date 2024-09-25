CREATE TABLE IF NOT EXISTS "AppUser"(
    "userId" SERIAL PRIMARY KEY,
    "fullName" VARCHAR(255),
    "username" VARCHAR(255) UNIQUE,
    "passwordHash" VARCHAR(255),
    "role" VARCHAR (255)
);

CREATE TABLE IF NOT EXISTS "UserEmail"(
    "userEmailId" SERIAL PRIMARY KEY,
    "userId" INT UNIQUE,
    "email" VARCHAR(255),
    CONSTRAINT fk_user FOREIGN KEY ("userId") REFERENCES "AppUser"("userId") ON DELETE CASCADE
);