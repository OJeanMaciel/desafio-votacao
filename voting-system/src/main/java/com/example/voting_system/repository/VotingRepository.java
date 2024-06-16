package com.example.voting_system.repository;

import com.example.voting_system.entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingRepository extends JpaRepository<Voting, Long> {
    boolean existsByCpfAndPautaId(String cpf, Long pautaId);

    int countByPautaIdAndVoto(Long id, String sim);

    boolean existsByPautaId(Long id);
}
