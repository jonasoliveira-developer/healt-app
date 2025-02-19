CREATE TABLE user(
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    profile JSON  NOT NULL
);

CREATE INDEX idx_user_email ON user (email);

INSERT INTO user (id, name, email, password, profile) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'João Silva', 'joao.silva@example.com', 'senhaSegura123', '["ADMIN", "USER"]');

INSERT INTO user (id, name, email, password, profile) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'Maria Oliveira', 'maria.oliveira@example.com', 'outraSenhaSegura456', '["USER"]');

INSERT INTO user (id, name, email, password, profile) VALUES
('550e8400-e29b-41d4-a716-446655440002', 'Carlos Souza', 'carlos.souza@example.com', 'senhaSuperSegura789', '["MODERATOR", "USER"]');

INSERT INTO user (id, name, email, password, profile) VALUES
('550e8400-e29b-41d4-a716-446655440003', 'Ana Pereira', 'ana.pereira@example.com', 'senhaUltraSegura012', '["ADMIN"]');

INSERT INTO user (id, name, email, password, profile) VALUES
('550e8400-e29b-41d4-a716-446655440004', 'Lucas Fernandes', 'lucas.fernandes@example.com', 'senhaMegaSegura345', '["USER", "MODERATOR"]');
