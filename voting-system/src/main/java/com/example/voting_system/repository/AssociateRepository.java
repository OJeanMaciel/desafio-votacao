package com.example.voting_system.repository;

import com.example.voting_system.entity.Associate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociateRepository extends JpaRepository<Associate, Long> {

    Optional<Associate> findByCpf(String cpf);
}
