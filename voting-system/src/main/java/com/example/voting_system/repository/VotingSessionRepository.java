package com.example.voting_system.repository;

import com.example.voting_system.entity.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
    Optional<Object> findByPautaId(Long pautaId);

    boolean existsByPautaId(Long pautaId);
}
