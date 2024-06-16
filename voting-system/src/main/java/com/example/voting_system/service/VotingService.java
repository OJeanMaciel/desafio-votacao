package com.example.voting_system.service;

import com.example.voting_system.controller.AssociateController;
import com.example.voting_system.dto.VotingDto;
import com.example.voting_system.entity.Associate;
import com.example.voting_system.entity.VotingSession;
import com.example.voting_system.entity.Voting;
import com.example.voting_system.exception.DuplicateVoteException;
import com.example.voting_system.exception.InvalidCpfException;
import com.example.voting_system.exception.InvalidVoteException;
import com.example.voting_system.exception.VotingSessionClosedException;
import com.example.voting_system.exception.VotingSessionNotFoundException;
import com.example.voting_system.repository.AssociateRepository;
import com.example.voting_system.repository.VotingRepository;
import com.example.voting_system.repository.VotingSessionRepository;
import com.example.voting_system.util.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class VotingService {
    private static final Logger logger = Logger.getLogger(AssociateController.class.getName());

    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private VotingSessionRepository votingSessionRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private FakeCpfValidatorService fakeCpfValidatorService;

    public VotingDto create(VotingDto votingDto) {
        String cleanCpf = CpfUtil.cleanCpf(votingDto.getCpf());

        // Valida o CPF e verifica se o associado pode votar
        fakeCpfValidatorService.checkUserAbilityToVote(cleanCpf);

        // Verifica se existe um associado com o CPF fornecido
        Associate associate = associateRepository.findByCpf(cleanCpf)
                .orElseThrow(() -> new InvalidCpfException("Associado não encontrado"));

        if (votingRepository.existsByCpfAndPautaId(cleanCpf, votingDto.getPautaId())) {
            logger.severe("Duplicate vote: " + votingDto);
            throw new DuplicateVoteException("Associado já votou nesta pauta");
        }

        VotingSession sessao = (VotingSession) votingSessionRepository.findByPautaId(votingDto.getPautaId())
                .orElseThrow(() -> new VotingSessionNotFoundException("Sessão de votação não encontrada ou já encerrada"));

        if (sessao.getDataEncerramento() != null && sessao.getDataEncerramento().isBefore(LocalDateTime.now())) {
            logger.severe("Voting session closed: " + votingDto);
            throw new VotingSessionClosedException("A sessão de votação já foi encerrada");
        }

        // Validação do voto
        List<String> validVotes = Arrays.asList("Sim", "Não");
        if (!validVotes.contains(votingDto.getVoto())) {
            logger.severe("Invalid vote: " + votingDto);
            throw new InvalidVoteException("Voto inválido. Aceito apenas 'Sim' ou 'Não'");
        }

        Voting voting = new Voting();
        voting.setPautaId(votingDto.getPautaId());
        voting.setAssociadoId(associate.getId()); // Usa o ID do associado encontrado
        voting.setCpf(cleanCpf);
        voting.setVoto(votingDto.getVoto());
        voting.setDataHoraVoto(LocalDate.now());
        voting = votingRepository.save(voting);

        logger.info("Created voting: " + voting.toString());
        return new VotingDto(voting.getId(), voting.getPautaId(), voting.getAssociadoId(), voting.getVoto(), voting.getCpf(), voting.getDataHoraVoto());
    }

    public List<VotingDto> getAllVoting() {
        logger.info("Request to get all voting");
        return votingRepository.findAll().stream()
                .map(voting -> new VotingDto(voting.getId(), voting.getPautaId(), voting.getAssociadoId(), voting.getVoto(), voting.getCpf(), voting.getDataHoraVoto()))
                .collect(Collectors.toList());
    }
}
