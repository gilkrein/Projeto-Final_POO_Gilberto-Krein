CREATE DATABASE IF NOT EXISTS agenda_db;

USE agenda_db;

CREATE TABLE IF NOT EXISTS contatos
 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100)
);

INSERT INTO contatos (nome, telefone, email) VALUES ('Usuario Teste', '11 99999-0000', 'teste@email.com');