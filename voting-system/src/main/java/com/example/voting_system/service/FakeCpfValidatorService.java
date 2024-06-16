package com.example.voting_system.service;

import com.example.voting_system.exception.InvalidCpfException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@Service
public class FakeCpfValidatorService {

    private final Random random = new Random();

    public boolean isValidCPF(String cpf) {
        // Simula a validação de CPF
        if (cpf == null || cpf.length() != 11) {
            throw new InvalidCpfException("CPF inválido");
        }
        return true;
    }

    public String checkUserAbilityToVote(String cpf) {
        isValidCPF(cpf);
        // Retorna aleatoriamente se o usuário pode votar ou não
        boolean ableToVote = random.nextBoolean();
        if (ableToVote) {
            return "ABLE_TO_VOTE";
        } else {
            throw new InvalidCpfException("UNABLE_TO_VOTE");
        }
    }
}
