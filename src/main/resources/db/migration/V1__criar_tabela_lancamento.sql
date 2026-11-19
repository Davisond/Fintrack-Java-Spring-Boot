CREATE TABLE lancamento (
    id        UUID           PRIMARY KEY,
    descricao VARCHAR(120)   NOT NULL,
    valor     NUMERIC(15, 2) NOT NULL CHECK (valor > 0),
    data      DATE           NOT NULL,
    tipo      VARCHAR(20)    NOT NULL CHECK (tipo IN ('RECEITA', 'DESPESA'))
);