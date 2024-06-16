package com.example.voting_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingDto {
    private Long id;

    private Long associadoId;

    private Long pautaId;

    private String voto;

    private String cpf;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataHoraVoto;
}
