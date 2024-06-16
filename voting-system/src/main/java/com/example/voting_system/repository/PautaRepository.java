package com.example.voting_system.repository;

import com.example.voting_system.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    List<Pauta> findAllByOrderByDataCriacaoAsc();
}
