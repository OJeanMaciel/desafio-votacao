-- Criação da tabela Associado
CREATE TABLE tb_associado (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL
);

-- Criação da tabela Pauta
CREATE TABLE tb_pauta (
    id SERIAL PRIMARY KEY,
    descricao TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação da tabela SessaoVotacao
CREATE TABLE tb_sessao_votacao (
    id SERIAL PRIMARY KEY,
    pauta_id INTEGER REFERENCES tb_pauta(id) ON DELETE CASCADE,
    data_abertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_encerramento TIMESTAMP
);

-- Criação da tabela Voto
CREATE TABLE tb_voto (
    id SERIAL PRIMARY KEY,
    associado_id INTEGER REFERENCES tb_associado(id) ON DELETE CASCADE,
    pauta_id INTEGER REFERENCES tb_pauta(id) ON DELETE CASCADE,
    voto VARCHAR(3) NOT NULL CHECK (voto IN ('Sim', 'Não')),
    data_hora_voto TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
