package com.example.voting_system.controller;

import com.example.voting_system.dto.AssociateDto;
import com.example.voting_system.service.AssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/associado")
public class AssociateController {
    private static final Logger logger = Logger.getLogger(AssociateController.class.getName());

    @Autowired
    private AssociateService associateService;

    @PostMapping
    public ResponseEntity<AssociateDto> create(@RequestBody AssociateDto associateDto) {
        logger.info("Request to create new associate: " + associateDto);
        AssociateDto createdAssociate = associateService.create(associateDto);
        logger.info("Created associate: " + createdAssociate);
        return ResponseEntity.ok(createdAssociate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociateDto> update(@PathVariable Long id, @RequestBody AssociateDto associateDto) {
        logger.info(String.format("Request to update associate with ID %d: %s", id, associateDto));
        AssociateDto updatedAssociate = associateService.update(id, associateDto);
        logger.info("Updated associate: " + updatedAssociate);
        return ResponseEntity.ok(updatedAssociate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociateDto> getAssociateById(@PathVariable Long id) {
        logger.info(String.format("Request to get associate by ID %d", id));
        AssociateDto associate = associateService.getAssociateById(id);
        logger.info("Retrieved associate: " + associate);
        return ResponseEntity.ok(associate);
    }

    @GetMapping
    public ResponseEntity<List<AssociateDto>> getAllAssociate() {
        logger.info("Request to get all associates");
        List<AssociateDto> associates = associateService.getAllAssociate();
        logger.info("Retrieved all associates");
        return ResponseEntity.ok(associates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info(String.format("Request to delete associate with ID %d", id));
        associateService.delete(id);
        logger.info("Deleted associate with ID " + id);
        return ResponseEntity.noContent().build();
    }
}
