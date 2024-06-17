package com.example.voting_system.repository;

import com.example.voting_system.entity.VotingSessionTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingSessionRepositoryTest extends JpaRepository<VotingSessionTest, Long> {
    boolean existsById(Long pautaId);
}