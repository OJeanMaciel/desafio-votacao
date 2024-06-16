package com.example.voting_system.controller;

import com.example.voting_system.dto.VotingDto;
import com.example.voting_system.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/votacao")
public class VotingController {
    private static final Logger logger = Logger.getLogger(AssociateController.class.getName());

    @Autowired
    private VotingService votingService;

    @PostMapping
    public ResponseEntity<VotingDto> create(@RequestBody VotingDto votingDto) {
        logger.info("Request to create new voting: " + votingDto);
        return ResponseEntity.ok(votingService.create(votingDto));
    }

    @GetMapping
    public ResponseEntity<List<VotingDto>> getAllVoting() {
        logger.info("Request to get all voting");
        return ResponseEntity.ok(votingService.getAllVoting());
    }
}