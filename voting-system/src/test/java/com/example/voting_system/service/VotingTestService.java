package com.example.voting_system.service;

import com.example.voting_system.repository.VoteRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

public class VotingTestService {

    @Autowired
    private VoteRepositoryTest voteRepositoryTest;

    public int countVotes(boolean decision) {
        return voteRepositoryTest.countByDecision(decision);
    }
}