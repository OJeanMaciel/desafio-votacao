package com.example.voting_system.controller;

import com.example.voting_system.dto.PautaDto;
import com.example.voting_system.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaDto> create(@RequestBody PautaDto pautaDto) {
        return ResponseEntity.ok(pautaService.create(pautaDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PautaDto> update(@PathVariable Long id, @RequestBody PautaDto pautaDto) {
        return ResponseEntity.ok(pautaService.update(id, pautaDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDto> getAgendaById(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.getAgendaById(id));
    }

    @GetMapping
    public ResponseEntity<List<PautaDto>> getAllAgenda() {
        return ResponseEntity.ok(pautaService.getAllAgenda());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pautaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
