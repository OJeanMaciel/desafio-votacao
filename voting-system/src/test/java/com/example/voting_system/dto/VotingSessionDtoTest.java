package com.example.voting_system.dto;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class VotingSessionDtoTest {
    @Id
    private Long id;
    private int pautaId;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataFinal;
    private int votosSim;
    private int votosNao;

    public VotingSessionDtoTest() {
        this.id = id;
        this.pautaId = pautaId;
        this.dataCriacao = dataCriacao;
        this.dataFinal = dataFinal;
        this.votosSim = votosSim;
        this.votosNao = votosNao;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPautaId() {
        return pautaId;
    }

    public void setPautaId(int i) {
        this.pautaId = pautaId;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getVotosSim() {
        return votosSim;
    }

    public void setVotosSim(int votosSim) {
        this.votosSim = votosSim;
    }

    public int getVotosNao() {
        return votosNao;
    }

    public void setVotosNao(int votosNao) {
        this.votosNao = votosNao;
    }
}
