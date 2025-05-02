CREATE TABLE user_domain_profile (
    user_domain_id CHAR(36) NOT NULL,
    profile VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_domain_id, profile),
    FOREIGN KEY (user_domain_id) REFERENCES user_domain(id)
);
