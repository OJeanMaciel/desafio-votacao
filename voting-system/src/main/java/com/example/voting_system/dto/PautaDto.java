package com.example.voting_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaDto {
    private Long id;

    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacao;

    public PautaDto(Long id) {
        this.id = id;
    }
}
