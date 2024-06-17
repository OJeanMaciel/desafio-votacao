package com.example.voting_system.repository;

import com.example.voting_system.entity.PautaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepositoryTest extends JpaRepository<PautaTest, Long> {
    boolean existsById(Long id);  // Correct method name
}
