package com.example.voting_system.repository;

import com.example.voting_system.entity.VoteTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepositoryTest extends JpaRepository<VoteTest, Long> {
    int countByDecision(boolean decision);

}
