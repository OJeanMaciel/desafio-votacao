package com.example.voting_system.exception;

public class VotingSessionNotFoundException extends RuntimeException {
    public VotingSessionNotFoundException(String message) {
        super(message);
    }
}