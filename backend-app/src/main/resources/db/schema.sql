CREATE TABLE fed_user (
    uuid VARCHAR(36) PRIMARY KEY NOT NULL,
    login_hash CHAR(64) NOT NULL,
    password_hash CHAR(64) NOT NULL,
    firstname VARCHAR(32) NOT NULL,
    lastname VARCHAR(32) NOT NULL,
    blocked BOOLEAN NOT NULL DEFAULT FALSE,
    type VARCHAR(32) NOT NULL
);
