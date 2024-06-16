package com.example.voting_system.service;

import com.example.voting_system.dto.AssociateDto;
import com.example.voting_system.entity.Associate;
import com.example.voting_system.exception.AssociateNotFoundException;
import com.example.voting_system.exception.InvalidCpfException;
import com.example.voting_system.repository.AssociateRepository;
import com.example.voting_system.util.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociateService {

    @Autowired
    private AssociateRepository associateRepository;

    public AssociateDto create(AssociateDto associateDto) {
        String cleanCpf = CpfUtil.cleanCpf(associateDto.getCpf());

        // Valida o CPF
        if (!CpfUtil.isValidCPF(associateDto.getCpf())) {
            throw new InvalidCpfException("CPF inválido");
        }

        Associate associate = new Associate();
        associate.setNome(associateDto.getNome());
        associate.setCpf(cleanCpf);
        associate = associateRepository.save(associate);
        return new AssociateDto(associate.getId(), associate.getNome(), associate.getCpf());
    }

    public AssociateDto update(Long id, AssociateDto associateDto) {
        Associate associate = associateRepository.findById(id).orElseThrow(() -> new AssociateNotFoundException("Associado com ID" + id + "não encontrado"));
        associate.setNome(associateDto.getNome());
        associate.setCpf(associateDto.getCpf());
        associate = associateRepository.save(associate);
        return new AssociateDto(associate.getId(), associate.getNome(), associate.getCpf());
    }

    public AssociateDto getAssociateById(Long id) {
        Associate associate = associateRepository.findById(id).orElseThrow(() -> new AssociateNotFoundException("Associado com ID" + id + "não encontrado"));
        return new AssociateDto(associate.getId(), associate.getNome(), null);
    }

    public List<AssociateDto> getAllAssociate() {
        return associateRepository.findAll().stream()
                .map(associate -> new AssociateDto(associate.getId(), associate.getNome(), null))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        associateRepository.deleteById(id);
    }
}