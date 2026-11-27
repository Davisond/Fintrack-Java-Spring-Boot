CREATE TABLE conta (
    id            UUID           PRIMARY KEY,
    nome          VARCHAR(60)    NOT NULL,
    saldo_inicial NUMERIC(15, 2) NOT NULL DEFAULT 0
);