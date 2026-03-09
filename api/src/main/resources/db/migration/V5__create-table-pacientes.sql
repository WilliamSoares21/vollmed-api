CREATE TABLE pacientes(

    id bigint NOT NULL auto_increment,
    nome varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    cpf varchar(14) NOT NULL UNIQUE,
    telefone varchar(20) NOT NULL,
    logradouro varchar(100) NOT NULL,
    bairro varchar(100) NOT NULL,
    cep varchar(9) NOT NULL,
    complemento varchar(100),
    numero varchar(20),
    uf char(2) NOT NULL,
    cidade varchar(100) NOT NULL,
    ativo tinyint NOT NULL,

    PRIMARY KEY(id)

);