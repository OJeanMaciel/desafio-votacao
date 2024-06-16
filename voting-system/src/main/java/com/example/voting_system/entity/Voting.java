package com.example.voting_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_voto")
public class Voting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "associado_id")
        private Long associadoId;

    @Column(name = "pauta_id")
    private Long pautaId;

    @Column(name = "voto")
    private String voto;

    @Column(name = "data_hora_voto")
    private LocalDate dataHoraVoto;

    @Column(name = "cpf")
    private String cpf;
}
