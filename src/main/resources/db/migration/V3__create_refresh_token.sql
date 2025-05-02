CREATE TABLE refresh_token_domain(
    id       CHAR(36) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL
);




