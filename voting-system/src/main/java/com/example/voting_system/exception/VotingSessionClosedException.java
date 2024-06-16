package com.example.voting_system.exception;

public class VotingSessionClosedException extends RuntimeException {
    public VotingSessionClosedException(String message) {
        super(message);
    }
}