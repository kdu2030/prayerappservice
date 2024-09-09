CREATE TABLE IF NOT EXISTS "_user"(
    "userId" SERIAL PRIMARY KEY,
    "firstName" VARCHAR(255),
    "lastName" VARCHAR(255),
    "email" VARCHAR(255) UNIQUE,
    "passwordHash" VARCHAR(255)
);