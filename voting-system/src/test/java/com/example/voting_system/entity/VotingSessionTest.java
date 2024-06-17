package com.example.voting_system.entity;

import com.example.voting_system.dto.PautaDtoTest;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class VotingSessionTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int pautaId;

    private LocalDateTime dataAbertura;
    private LocalDateTime dataEncerramento;

    public VotingSessionTest() {
    }

    public VotingSessionTest(Long id, int pautaId, LocalDateTime dataAbertura, LocalDateTime dataEncerramento) {
        this.id = id;
        this.pautaId = pautaId;
        this.dataAbertura = dataAbertura;
        this.dataEncerramento = dataEncerramento;
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

    public void setPautaId(int pautaId) {
        this.pautaId = pautaId;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDateTime dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }
}
