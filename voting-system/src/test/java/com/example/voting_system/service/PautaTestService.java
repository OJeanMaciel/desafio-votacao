package com.example.voting_system.service;

import com.example.voting_system.dto.PautaDtoTest;
import com.example.voting_system.entity.PautaTest;
import com.example.voting_system.exception.AgendaNotFoundException;
import com.example.voting_system.repository.PautaRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PautaTestService {

    @Autowired
    private PautaRepositoryTest pautaRepositoryTest;

    public PautaDtoTest create(PautaDtoTest dto) {
        PautaTest pautaTest = new PautaTest(null, dto.getDescription(), LocalDate.now());
        PautaTest saved = pautaRepositoryTest.save(pautaTest);
        return new PautaDtoTest(saved.getId(), saved.getDescription(), saved.getDate());
    }

    public PautaDtoTest update(Long id, PautaDtoTest dto) throws AgendaNotFoundException {
        PautaTest pautaTest = pautaRepositoryTest.findById(id)
                .orElseThrow(() -> new AgendaNotFoundException("Pauta com ID" + id + " não encontrado"));
        pautaTest.setDescription(dto.getDescription());
        pautaTest = pautaRepositoryTest.save(pautaTest);
        return new PautaDtoTest(pautaTest.getId(), pautaTest.getDescription(), pautaTest.getDate());
    }

    public void delete(Long id) throws RuntimeException {
        if (pautaRepositoryTest.existsById(id)) {
            throw new RuntimeException("Não é possível excluir uma pauta que tem votos ou sessões associadas.");
        }
        pautaRepositoryTest.deleteById(id);
    }
}
