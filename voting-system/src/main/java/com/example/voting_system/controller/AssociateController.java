package com.example.voting_system.controller;

import com.example.voting_system.dto.AssociateDto;
import com.example.voting_system.service.AssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/associado")
public class AssociateController {

    @Autowired
    private AssociateService associateService;

    @PostMapping
    public ResponseEntity<AssociateDto> create(@RequestBody AssociateDto associateDto) {
        return ResponseEntity.ok(associateService.create(associateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociateDto> update(@PathVariable Long id, @RequestBody AssociateDto associateDto) {
        return ResponseEntity.ok(associateService.update(id, associateDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociateDto> getAssociateById(@PathVariable Long id) {
        return ResponseEntity.ok(associateService.getAssociateById(id));
    }

    @GetMapping
    public ResponseEntity<List<AssociateDto>> getAllAssociate() {
        List<AssociateDto> associates = associateService.getAllAssociate();
        return ResponseEntity.ok(associates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        associateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}