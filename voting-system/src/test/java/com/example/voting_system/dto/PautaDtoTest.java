package com.example.voting_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaDtoTest {

    private Long id;

    private String description;

    private LocalDate date;
}
