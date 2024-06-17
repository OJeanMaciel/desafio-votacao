package com.example.voting_system.service;

import com.example.voting_system.dto.AssociateDtoTest;
import com.example.voting_system.entity.AssociateTest;
import com.example.voting_system.exception.InvalidCpfException;
import com.example.voting_system.repository.AssociateRepositoryTest;
import com.example.voting_system.util.CpfUtilTest;
import org.springframework.beans.factory.annotation.Autowired;

public class AssociateTestService {
    @Autowired
    private AssociateRepositoryTest associateRepository;

    public AssociateDtoTest create(AssociateDtoTest input) {
        if (!CpfUtilTest.isValidCPF(input.getCpf())) {
            throw new InvalidCpfException("Invalid CPF");
        }
        AssociateTest newAssociate = new AssociateTest(null, input.getNome(), input.getCpf());
        AssociateTest savedAssociate = associateRepository.save(newAssociate);
        if (savedAssociate == null) {
            throw new RuntimeException("Failed to save the associate");
        }
        return new AssociateDtoTest(savedAssociate.getId(), savedAssociate.getNome(), savedAssociate.getCpf());
    }
}

