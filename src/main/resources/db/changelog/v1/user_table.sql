CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL DEFAULT ('{noop}123'),
                       role user_role NOT NULL
);

INSERT INTO users (username, password, role) VALUES ('user', '{noop}123', 'USER');
INSERT INTO users (username, password, role) VALUES ('admin', '{noop}123', 'ADMIN');
