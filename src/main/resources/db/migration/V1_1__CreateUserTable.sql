CREATE TABLE IF NOT EXISTS "_user"(
    "userId" SERIAL PRIMARY KEY,
    "fullName" VARCHAR(255),
    "username" VARCHAR(255) UNIQUE,
    "email" VARCHAR(255) UNIQUE,
    "passwordHash" VARCHAR(255)
);