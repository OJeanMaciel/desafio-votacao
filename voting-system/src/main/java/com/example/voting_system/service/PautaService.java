package com.example.voting_system.service;

import com.example.voting_system.dto.PautaDto;
import com.example.voting_system.entity.Pauta;
import com.example.voting_system.exception.AgendaNotFoundException;
import com.example.voting_system.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

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
        pautaRepository.deleteById(id);
    }
}
