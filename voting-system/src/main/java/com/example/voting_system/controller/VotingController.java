package com.example.voting_system.controller;

import com.example.voting_system.dto.VotingDto;
import com.example.voting_system.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/votacao")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @PostMapping
    public ResponseEntity<VotingDto> create(@RequestBody VotingDto votingDto) {
        return ResponseEntity.ok(votingService.create(votingDto));
    }

    @GetMapping
    public ResponseEntity<List<VotingDto>> getAllVoting() {
        return ResponseEntity.ok(votingService.getAllVoting());
    }
}