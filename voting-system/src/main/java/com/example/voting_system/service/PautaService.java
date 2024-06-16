package com.example.voting_system.service;

import com.example.voting_system.dto.PautaDto;
import com.example.voting_system.entity.Pauta;
import com.example.voting_system.exception.AgendaNotFoundException;
import com.example.voting_system.repository.PautaRepository;
import com.example.voting_system.repository.VotingRepository;
import com.example.voting_system.repository.VotingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PautaService {
    private static final Logger logger = Logger.getLogger(AssociateService.class.getName());

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private VotingSessionRepository votingSessionRepository;

    public PautaDto create(PautaDto pautaDto) {
        Pauta pauta = new Pauta();
        pauta.setDescricao(pautaDto.getDescricao());
        pauta.setDataCriacao(LocalDate.now());
        pauta = pautaRepository.save(pauta);
        logger.info("Created agenda: " + pauta.toString());
        return new PautaDto(pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao());
    }

    public PautaDto update(Long id, PautaDto pautaDto) {
        Pauta pauta = pautaRepository.findById(id).orElseThrow(() -> new AgendaNotFoundException("Pauta com ID" + id + "não encontrado"));
        pauta.setDescricao(pautaDto.getDescricao());
        pauta = pautaRepository.save(pauta);
        logger.info("Updated agenda: " + pauta.toString());
        return new PautaDto(pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao());
    }

    public PautaDto getAgendaById(Long id) {
        Pauta pauta = pautaRepository.findById(id).orElseThrow(() -> new AgendaNotFoundException("Pauta com ID" + id + "não encontrado"));
        logger.info("Retrieved agenda: " + pauta.toString());
        return new PautaDto(pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao());
    }

    public List<PautaDto> getAllAgenda() {
        return pautaRepository.findAllByOrderByDataCriacaoAsc().stream()
                .map(pauta -> {
                    logger.info("Retrieved agenda: " + pauta.toString());
                    return new PautaDto(pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao());
                })
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> new AgendaNotFoundException("Pauta com ID" + id + "não encontrado"));

        if (votingRepository.existsByPautaId(pauta.getId())) {
            logger.severe("Pauta com ID " + id + " tem votos associados.");
            throw new RuntimeException("Não é possível excluir uma pauta que tem votos associados.");
        }

        if (votingSessionRepository.existsByPautaId(pauta.getId())) {
            logger.severe("Pauta com ID " + id + " tem sessões de votação associadas.");
            throw new RuntimeException("Não é possível excluir uma pauta que tem sessões de votação associadas.");
        }

        logger.info("Deleted agenda with ID " + id);
        pautaRepository.deleteById(id);
    }
}
