package com.example.voting_system.service;

import com.example.voting_system.controller.AssociateController;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class VotingSessionService {
    private static final Logger logger = Logger.getLogger(AssociateController.class.getName());

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
            logger.severe("Duplicate voting session: " + votingSessionDto);
            throw new DuplicateVotingSessionException("Já existe uma sessão de votação para esta pauta.");
        }

        if (votingSessionDto.getDataAbertura() == null) {
            logger.severe("Opening date cannot be null: " + votingSessionDto);
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

        logger.info("Voting session created: " + votingSession);
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
                .orElseThrow(() -> {
                    logger.severe("Voting session not found: " + id);
                    return new VotingSessionNotFoundException("Sessão de votação " + id + " não encontrada!");
                });
        Pauta pauta = pautaRepository.findById(votingSessionDto.getPauta().getId())
                .orElseThrow(() -> {
                    logger.severe("Pauta not found: " + votingSessionDto.getPauta().getId());
                    return new AgendaNotFoundException("Pauta não encontrada!");
                });

        if (votingSessionDto.getDataAbertura() == null) {
            logger.severe("Opening date cannot be null: " + votingSessionDto);
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

        logger.info("Voting session updated: " + votingSession.toString());
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
        logger.info("Get all voting sessions");
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
            logger.severe("Voting session with ID " + id + " has votes associated.");
            throw new RuntimeException("Não é possível excluir uma sessão que tem votos associados.");
        }

        logger.info("Deleted voting session with ID " + id);
        votingSessionRepository.deleteById(id);
    }

}
