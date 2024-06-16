package com.example.voting_system.controller;

import com.example.voting_system.dto.VotingSessionDto;
import com.example.voting_system.service.VotingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/sessao")
public class VotingSessionController {
    private static final Logger logger = Logger.getLogger(AssociateController.class.getName());

    @Autowired
    private VotingSessionService votingSessionService;

    @PostMapping
    public ResponseEntity<VotingSessionDto> create(@RequestBody VotingSessionDto votingSessionDto) {
        logger.info("Request to create new voting session: " + votingSessionDto);
        return ResponseEntity.ok(votingSessionService.create(votingSessionDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VotingSessionDto> update(@PathVariable Long id, @RequestBody VotingSessionDto votingSessionDto) {
        logger.info(String.format("Request to update voting session with ID %d: %s", id, votingSessionDto));
        return ResponseEntity.ok(votingSessionService.update(id, votingSessionDto));
    }

    @GetMapping
    public ResponseEntity<List<VotingSessionDto>> getAllVotingSession() {
        logger.info("Request to get all voting session");
        return ResponseEntity.ok(votingSessionService.getAllVotingSession());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info(String.format("Request to delete voting session with ID %d", id));
        votingSessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}