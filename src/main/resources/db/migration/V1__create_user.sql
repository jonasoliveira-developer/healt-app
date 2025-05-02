CREATE TABLE user_domain(
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL

);

CREATE INDEX idx_user_email ON user_domain (email);



