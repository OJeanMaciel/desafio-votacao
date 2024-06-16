package com.example.voting_system.service;

import com.example.voting_system.dto.VotingDto;
import com.example.voting_system.entity.VotingSession;
import com.example.voting_system.entity.Voting;
import com.example.voting_system.exception.DuplicateVoteException;
import com.example.voting_system.exception.InvalidCpfException;
import com.example.voting_system.exception.VotingSessionClosedException;
import com.example.voting_system.exception.VotingSessionNotFoundException;
import com.example.voting_system.repository.VotingSessionRepository;
import com.example.voting_system.repository.VotingRepository;
import com.example.voting_system.util.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VotingService {

    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private VotingSessionRepository votingSessionRepository;

    public VotingDto create(VotingDto votingDto) {
        String cleanCpf = CpfUtil.cleanCpf(votingDto.getCpf());

        // Valida o CPF
        if (!CpfUtil.isValidCPF(votingDto.getCpf())) {
            throw new InvalidCpfException("CPF inválido");
        }

        // Verifica se o associado já votou nesta pauta
        if (votingRepository.existsByCpfAndPautaId(votingDto.getCpf(), votingDto.getPautaId())) {
            throw new DuplicateVoteException("Associado já votou nesta pauta");
        }

        // Busca a sessão de votação para verificar a data de encerramento
        VotingSession sessao = (VotingSession) votingSessionRepository.findByPautaId(votingDto.getPautaId())
                .orElseThrow(() -> new VotingSessionNotFoundException("Sessão de votação não encontrada ou já encerrada"));

        if (sessao.getDataEncerramento() != null && sessao.getDataEncerramento().isBefore(LocalDate.now())) {
            throw new VotingSessionClosedException("A sessão de votação já foi encerrada");
        }

        Voting voting = new Voting();
        voting.setPautaId(votingDto.getPautaId());
        voting.setAssociadoId(votingDto.getAssociadoId());
        voting.setCpf(cleanCpf);
        voting.setVoto(votingDto.getVoto());
        voting.setDataHoraVoto(LocalDate.now());
        voting = votingRepository.save(voting);

        return new VotingDto(voting.getId(), voting.getPautaId(), voting.getAssociadoId(), voting.getVoto(), voting.getCpf(), voting.getDataHoraVoto());
    }

    public List<VotingDto> getAllVoting() {
        return votingRepository.findAll().stream()
                .map(voting -> new VotingDto(voting.getId(), voting.getPautaId(), voting.getAssociadoId(), voting.getVoto(), voting.getCpf(), voting.getDataHoraVoto()))
                .collect(Collectors.toList());
    }
}