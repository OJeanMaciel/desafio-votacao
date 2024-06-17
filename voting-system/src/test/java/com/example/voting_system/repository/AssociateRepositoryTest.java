package com.example.voting_system.repository;

import com.example.voting_system.entity.AssociateTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociateRepositoryTest extends JpaRepository<AssociateTest, Long> {
}