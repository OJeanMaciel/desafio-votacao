ALTER TABLE tb_voto ADD cpf VARCHAR(11);

ALTER TABLE tb_voto ADD CONSTRAINT fk_voto_cpf FOREIGN KEY (cpf) REFERENCES tb_associado(cpf);

ALTER TABLE tb_voto ADD CONSTRAINT unique_cpf_pauta UNIQUE (cpf, pauta_id);
