package com.example.voting_system.controller;

import com.example.voting_system.dto.PautaDto;
import com.example.voting_system.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/pautas")
public class PautaController {
    private static final Logger logger = Logger.getLogger(AssociateController.class.getName());

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaDto> create(@RequestBody PautaDto pautaDto) {
        logger.info("Request to create new agenda: " + pautaDto);
        return ResponseEntity.ok(pautaService.create(pautaDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PautaDto> update(@PathVariable Long id, @RequestBody PautaDto pautaDto) {
        logger.info(String.format("Request to update agenda with ID %d: %s", id, pautaDto));
        return ResponseEntity.ok(pautaService.update(id, pautaDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDto> getAgendaById(@PathVariable Long id) {
        logger.info(String.format("Request to get agenda by ID %d", id));
        return ResponseEntity.ok(pautaService.getAgendaById(id));
    }

    @GetMapping
    public ResponseEntity<List<PautaDto>> getAllAgenda() {
        logger.info("Request to get all agendas");
        return ResponseEntity.ok(pautaService.getAllAgenda());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info(String.format("Request to delete agenda with ID %d", id));
        pautaService.delete(id);
        logger.info("Deleted agenda with ID " + id);
        return ResponseEntity.noContent().build();
    }
}
