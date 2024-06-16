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
import java.util.stream.Collectors;

@Service
public class PautaService {

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
        return new PautaDto(pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao());
    }

    public PautaDto update(Long id, PautaDto pautaDto) {
        Pauta pauta = pautaRepository.findById(id).orElseThrow(() -> new AgendaNotFoundException("Pauta com ID" + id + "não encontrado"));
        pauta.setDescricao(pautaDto.getDescricao());
        pauta = pautaRepository.save(pauta);
        return new PautaDto(pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao());
    }

    public PautaDto getAgendaById(Long id) {
        Pauta pauta = pautaRepository.findById(id).orElseThrow(() -> new AgendaNotFoundException("Pauta com ID" + id + "não encontrado"));
        return new PautaDto(pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao());
    }

    public List<PautaDto> getAllAgenda() {
        return pautaRepository.findAllByOrderByDataCriacaoAsc().stream()
                .map(pauta -> new PautaDto(pauta.getId(), pauta.getDescricao(), pauta.getDataCriacao()))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> new AgendaNotFoundException("Pauta com ID" + id + "não encontrado"));

        if (votingRepository.existsByPautaId(pauta.getId())) {
            throw new RuntimeException("Não é possível excluir uma pauta que tem votos associados.");
        }

        if (votingSessionRepository.existsByPautaId(pauta.getId())) {
            throw new RuntimeException("Não é possível excluir uma pauta que tem sessões de votação associadas.");
        }

        pautaRepository.deleteById(id);
    }
}
