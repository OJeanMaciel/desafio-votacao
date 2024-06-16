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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AssociateService {
    private static final Logger logger = Logger.getLogger(AssociateService.class.getName());

    @Autowired
    private AssociateRepository associateRepository;

    public AssociateDto create(AssociateDto associateDto) {
        String cleanCpf = CpfUtil.cleanCpf(associateDto.getCpf());

        // Valida o CPF
        if (!CpfUtil.isValidCPF(cleanCpf)) {
            logger.severe("Invalid CPF: " + associateDto.getCpf());
            throw new InvalidCpfException("CPF inválido");
        }

        Associate associate = new Associate();
        associate.setNome(associateDto.getNome());
        associate.setCpf(cleanCpf);
        associate = associateRepository.save(associate);
        logger.info("Created associate: " + associate.toString());
        return new AssociateDto(associate.getId(), associate.getNome(), associate.getCpf());
    }

    public AssociateDto update(Long id, AssociateDto associateDto) {
        Associate associate = associateRepository.findById(id).orElseThrow(() -> {
            logger.severe("Associate not found with ID " + id);
            return new AssociateNotFoundException("Associado com ID" + id + "não encontrado");
        });
        associate.setNome(associateDto.getNome());
        associate.setCpf(associateDto.getCpf());
        associate = associateRepository.save(associate);
        logger.info("Updated associate: " + associate.toString());
        return new AssociateDto(associate.getId(), associate.getNome(), associate.getCpf());
    }

    public AssociateDto getAssociateById(Long id) {
        Associate associate = associateRepository.findById(id).orElseThrow(() -> {
            logger.severe("Associate not found with ID " + id);
            return new AssociateNotFoundException("Associado com ID" + id + "não encontrado");
        });
        logger.info("Retrieved associate: " + associate.toString());
        return new AssociateDto(associate.getId(), associate.getNome(), null);
    }

    public List<AssociateDto> getAllAssociate() {
        List<AssociateDto> associates = associateRepository.findAll().stream()
                .map(associate -> new AssociateDto(associate.getId(), associate.getNome(), null))
                .collect(Collectors.toList());
        logger.info("Retrieved all associates");
        return associates;
    }

    public void delete(Long id) {
        associateRepository.deleteById(id);
        logger.info("Deleted associate with ID " + id);
    }
}
