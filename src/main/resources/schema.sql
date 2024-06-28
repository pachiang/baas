CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Drop tables if they exist
DROP TABLE IF EXISTS system_user CASCADE;
DROP TABLE IF EXISTS goods CASCADE;

-- Create table system_user
CREATE TABLE system_user (
    _id UUID PRIMARY KEY,
    account VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    name VARCHAR(128)
);

-- Create table goods
CREATE TABLE goods (
    _id UUID PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    cr_user UUID NOT NULL,
    cr_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    up_user UUID NOT NULL,
    up_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cr_user) REFERENCES system_user(_id),
    FOREIGN KEY (up_user) REFERENCES system_user(_id)
);