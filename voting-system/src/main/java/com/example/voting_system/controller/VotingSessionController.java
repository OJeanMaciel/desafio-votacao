package com.example.voting_system.controller;

import com.example.voting_system.dto.VotingSessionDto;
import com.example.voting_system.service.VotingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/sessao")
public class VotingSessionController {

    @Autowired
    private VotingSessionService votingSessionService;

    @PostMapping
    public ResponseEntity<VotingSessionDto> create(@RequestBody VotingSessionDto votingSessionDto) {
        return ResponseEntity.ok(votingSessionService.create(votingSessionDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VotingSessionDto> update(@PathVariable Long id, @RequestBody VotingSessionDto votingSessionDto) {
        return ResponseEntity.ok(votingSessionService.update(id, votingSessionDto));
    }

    @GetMapping
    public ResponseEntity<List<VotingSessionDto>> getAllVotingSession() {
        return ResponseEntity.ok(votingSessionService.getAllVotingSession());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        votingSessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}