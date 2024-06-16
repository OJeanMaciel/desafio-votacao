package com.example.voting_system.service;

import com.example.voting_system.dto.PautaDto;
import com.example.voting_system.dto.VotingSessionDto;
import com.example.voting_system.entity.Pauta;
import com.example.voting_system.entity.VotingSession;
import com.example.voting_system.exception.AgendaNotFoundException;
import com.example.voting_system.exception.InvalidDateException;
import com.example.voting_system.exception.DuplicateVotingSessionException; // nova exceção para sessões duplicadas
import com.example.voting_system.exception.VotingSessionNotFoundException;
import com.example.voting_system.repository.PautaRepository;
import com.example.voting_system.repository.VotingSessionRepository;
import com.example.voting_system.repository.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VotingSessionService {

    @Autowired
    private VotingSessionRepository votingSessionRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VotingRepository votingRepository;

    public VotingSessionDto create(VotingSessionDto votingSessionDto) {
        Pauta pauta = pautaRepository.findById(votingSessionDto.getPauta().getId())
                .orElseThrow(() -> new AgendaNotFoundException("Pauta não encontrada!"));

        // Verificação para garantir que não haja sessões duplicadas para a mesma pauta
        if (votingSessionRepository.existsByPautaId(pauta.getId())) {
            throw new DuplicateVotingSessionException("Já existe uma sessão de votação para esta pauta.");
        }

        if (votingSessionDto.getDataAbertura() == null) {
            throw new InvalidDateException("Data de abertura não pode ser nula");
        }

        VotingSession votingSession = new VotingSession();
        votingSession.setPauta(pauta);
        votingSession.setDataAbertura(votingSessionDto.getDataAbertura());
        votingSession.setDataEncerramento(
                votingSessionDto.getDataEncerramento() == null ?
                        votingSessionDto.getDataAbertura().plusMinutes(1) :
                        votingSessionDto.getDataEncerramento()
        );

        votingSession = votingSessionRepository.save(votingSession);

        return new VotingSessionDto(
                votingSession.getId(),
                new PautaDto(votingSession.getPauta().getId(), votingSession.getPauta().getDescricao(), votingSession.getPauta().getDataCriacao()),
                votingSession.getDataAbertura(),
                votingSession.getDataEncerramento(),
                0,
                0
        );
    }

    public VotingSessionDto update(Long id, VotingSessionDto votingSessionDto) {
        VotingSession votingSession = votingSessionRepository.findById(id)
                .orElseThrow(() -> new VotingSessionNotFoundException("Sessão de votação " + id + " não encontrada!"));
        Pauta pauta = pautaRepository.findById(votingSessionDto.getPauta().getId())
                .orElseThrow(() -> new AgendaNotFoundException("Pauta não encontrada!"));

        if (votingSessionDto.getDataAbertura() == null) {
            throw new InvalidDateException("Data de abertura não pode ser nula");
        }

        votingSession.setPauta(pauta);
        votingSession.setDataAbertura(votingSessionDto.getDataAbertura());
        votingSession.setDataEncerramento(
                votingSessionDto.getDataEncerramento() == null ?
                        votingSessionDto.getDataAbertura().plusMinutes(1) :
                        votingSessionDto.getDataEncerramento()
        );

        votingSession = votingSessionRepository.save(votingSession);

        return new VotingSessionDto(
                votingSession.getId(),
                new PautaDto(votingSession.getPauta().getId(), votingSession.getPauta().getDescricao(), votingSession.getPauta().getDataCriacao()),
                votingSession.getDataAbertura(),
                votingSession.getDataEncerramento(),
                0,
                0
        );
    }

    public List<VotingSessionDto> getAllVotingSession() {
        return votingSessionRepository.findAll().stream()
                .map(sessao -> {
                    int totalSim = votingRepository.countByPautaIdAndVoto(sessao.getPauta().getId(), "Sim");
                    int totalNao = votingRepository.countByPautaIdAndVoto(sessao.getPauta().getId(), "Não");
                    return new VotingSessionDto(
                            sessao.getId(),
                            new PautaDto(sessao.getPauta().getId(), sessao.getPauta().getDescricao(), sessao.getPauta().getDataCriacao()),
                            sessao.getDataAbertura(),
                            sessao.getDataEncerramento(),
                            totalSim,
                            totalNao);
                })
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        VotingSession votingSession = votingSessionRepository.findById(id)
                .orElseThrow(() -> new VotingSessionNotFoundException("Sessão de votação " + id + " não encontrada!"));

        if (votingRepository.existsByPautaId(votingSession.getPauta().getId())) {
            throw new RuntimeException("Não é possível excluir uma sessão que tem votos associados.");
        }

        votingSessionRepository.deleteById(id);
    }

}
