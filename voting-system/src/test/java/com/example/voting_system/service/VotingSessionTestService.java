package com.example.voting_system.service;

import com.example.voting_system.dto.PautaDtoTest;
import com.example.voting_system.dto.VotingSessionDtoTest;
import com.example.voting_system.entity.PautaTest;
import com.example.voting_system.entity.VotingSessionTest;
import com.example.voting_system.exception.AgendaNotFoundException;
import com.example.voting_system.exception.DuplicateVotingSessionException;
import com.example.voting_system.exception.InvalidDateException;
import com.example.voting_system.repository.PautaRepositoryTest;
import com.example.voting_system.repository.VotingSessionRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VotingSessionTestService {

    @Autowired
    private VotingSessionRepositoryTest votingSessionRepositoryTest;

    @Autowired
    private PautaRepositoryTest pautaRepositoryTest;

    public VotingSessionDtoTest create(VotingSessionDtoTest dto) {
        PautaTest pautaTest = pautaRepositoryTest.findById(dto.getId())
                .orElseThrow(() -> new AgendaNotFoundException("Pauta não encontrada!"));

        if (votingSessionRepositoryTest.existsById(dto.getId())) {
            throw new DuplicateVotingSessionException("Já existe uma sessão de votação para esta pauta.");
        }

        if (dto.getDataCriacao() == null) {
            throw new InvalidDateException("Data de abertura não pode ser nula");
        }

        VotingSessionTest session = new VotingSessionTest(null, dto.getPautaId(), dto.getDataCriacao(),
                dto.getDataFinal() == null ? dto.getDataCriacao().plusMinutes(1) : dto.getDataFinal());

        session = votingSessionRepositoryTest.save(session);

        return new VotingSessionDtoTest();
    }
}
