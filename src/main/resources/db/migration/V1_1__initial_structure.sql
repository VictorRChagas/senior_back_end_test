CREATE TABLE ITEM(
    ID   VARCHAR(100)  NOT NULL,
    DESCRICAO VARCHAR(120)  NOT NULL,
    INATIVO BIT NOT NULL DEFAULT FALSE
);

CREATE TABLE PEDIDO (
    ID   VARCHAR(100)  NOT NULL,
    VALOR_TOTAL_PEDIDO DOUBLE NOT NULL,
    STATUS ENUM('ABERTO', 'APROVADO') NOT NULL
);

CREATE TABLE VENDA (
    ID   VARCHAR(100)  NOT NULL,
    TIPO_VENDA ENUM('PRODUTO', 'SERVICO') NOT NULL,
    VALOR_TOTAL_VENDA DOUBLE NOT NULL,
    VALOR_DESCONTO DOUBLE,
    DESCRICAO_VENDA VARCHAR(120),
    ID_ITEM VARCHAR(120),
    ID_PEDIDO VARCHAR(120),
    CONSTRAINT FK_ITEM FOREIGN KEY (ID_ITEM) REFERENCES ITEM (ID),
    CONSTRAINT FK_PEDIDO FOREIGN KEY (ID_PEDIDO) REFERENCES PEDIDO (ID)
);

