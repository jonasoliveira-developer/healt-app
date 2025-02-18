CREATE TABLE user(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    profile JSON  NOT NULL
);

CREATE INDEX idx_user_email ON user (email);