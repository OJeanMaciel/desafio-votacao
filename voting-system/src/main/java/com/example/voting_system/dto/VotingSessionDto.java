package com.example.voting_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingSessionDto {
    private Long id;

    private PautaDto pauta;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAbertura;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEncerramento;

    private int totalSim;

    private int totalNao;

}
