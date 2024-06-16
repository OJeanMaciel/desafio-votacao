package com.example.voting_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_sessao_votacao")
public class VotingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pauta_id", referencedColumnName = "id")
    private Pauta pauta;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_encerramento")
    private LocalDateTime dataEncerramento;
}
