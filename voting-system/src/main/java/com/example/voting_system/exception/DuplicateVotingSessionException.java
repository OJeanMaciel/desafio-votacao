package com.example.voting_system.exception;

public class DuplicateVotingSessionException  extends RuntimeException {
    public DuplicateVotingSessionException(String message) {
        super(message);
    }
}