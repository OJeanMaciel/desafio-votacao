package com.example.voting_system;

import com.example.voting_system.dto.AssociateDtoTest;
import com.example.voting_system.dto.PautaDtoTest;
import com.example.voting_system.dto.VotingSessionDtoTest;
import com.example.voting_system.entity.AssociateTest;
import com.example.voting_system.entity.PautaTest;
import com.example.voting_system.entity.VotingSessionTest;
import com.example.voting_system.exception.AgendaNotFoundException;
import com.example.voting_system.exception.DuplicateVotingSessionException;
import com.example.voting_system.exception.InvalidCpfException;
import com.example.voting_system.exception.VotingSessionNotFoundException;
import com.example.voting_system.repository.AssociateRepositoryTest;
import com.example.voting_system.repository.PautaRepositoryTest;
import com.example.voting_system.repository.VoteRepositoryTest;
import com.example.voting_system.repository.VotingSessionRepositoryTest;
import com.example.voting_system.service.AssociateTestService;
import com.example.voting_system.service.PautaTestService;
import com.example.voting_system.service.VotingSessionTestService;
import com.example.voting_system.service.VotingTestService;
import com.example.voting_system.util.CpfUtilTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class VotingSystemApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private VoteRepositoryTest voteRepositoryTest;

	@InjectMocks
	private VotingTestService votingTestService;

	@Test
	public void testVoteCounting() {
		when(voteRepositoryTest.countByDecision(true)).thenReturn(10);
		assertEquals(10, votingTestService.countVotes(true));
	}

	@Mock
	private AssociateRepositoryTest associateRepository;

	@InjectMocks
	private AssociateTestService associateService;

	@Test
	public void testCreateAssociateWithValidCpf() {
		AssociateDtoTest input = new AssociateDtoTest(null, "John Doe", "valid-cpf");
		AssociateTest output = new AssociateTest(input.getId(), input.getNome(), input.getCpf());

		try (MockedStatic<CpfUtilTest> mockedCpfUtil = Mockito.mockStatic(CpfUtilTest.class)) {
			mockedCpfUtil.when(() -> CpfUtilTest.isValidCPF(input.getCpf())).thenReturn(true);
			when(associateRepository.save(any(AssociateTest.class))).thenReturn(output);

			AssociateDtoTest result = associateService.create(input);
			assertEquals("John Doe", result.getNome());
			verify(associateRepository).save(any(AssociateTest.class));
		}
	}

	@Test
	public void testCreateAssociateWithInvalidCpf() {
		AssociateDtoTest input = new AssociateDtoTest(null, "John Doe", "invalid-cpf");

		try (MockedStatic<CpfUtilTest> mockedCpfUtil = Mockito.mockStatic(CpfUtilTest.class)) {
			mockedCpfUtil.when(() -> CpfUtilTest.isValidCPF(input.getCpf())).thenReturn(false);

			Exception exception = assertThrows(InvalidCpfException.class, () -> {
				associateService.create(input);
			});

			assertNotNull(exception);
			assertEquals("Invalid CPF", exception.getMessage());
		}
	}

	@Mock
	private PautaRepositoryTest pautaRepositoryTest;

	@InjectMocks
	private PautaTestService pautaTestService;

	@Test
	void testCreateSuccess() {
		PautaTest pauta = new PautaTest(null, "Test Description", LocalDate.now());
		when(pautaRepositoryTest.save(any(PautaTest.class))).thenReturn(pauta);
		PautaDtoTest result = pautaTestService.create(new PautaDtoTest(null, "Test Description", null));
		assertNotNull(result);
		assertEquals("Test Description", result.getDescription());
	}

	@Test
	void testUpdateNotFound() {
		Long pautaId = 1L;
		PautaDtoTest updateDto = new PautaDtoTest(pautaId, "Updated Description", null);
		when(pautaRepositoryTest.findById(pautaId)).thenReturn(Optional.empty());

		assertThrows(AgendaNotFoundException.class, () -> pautaTestService.update(pautaId, updateDto));
	}

	@Test
	void testUpdateSuccess() {
		Long pautaId = 1L;
		PautaTest existingPauta = new PautaTest(pautaId, "Original Description", LocalDate.now());
		PautaTest updatedPauta = new PautaTest(pautaId, "Updated Description", LocalDate.now());
		when(pautaRepositoryTest.findById(pautaId)).thenReturn(Optional.of(existingPauta));
		when(pautaRepositoryTest.save(any(PautaTest.class))).thenReturn(updatedPauta);

		PautaDtoTest result = pautaTestService.update(pautaId, new PautaDtoTest(pautaId, "Updated Description", null));

		assertNotNull(result);
		assertEquals("Updated Description", result.getDescription());
	}

	@Test
	void testDeleteWithVotes() {
		Long pautaId = 1L;
		when(pautaRepositoryTest.findById(pautaId)).thenReturn(Optional.of(new PautaTest(pautaId, "Test Pauta", LocalDate.now())));
		when(pautaRepositoryTest.existsById(pautaId)).thenReturn(true);

		assertThrows(RuntimeException.class, () -> pautaTestService.delete(pautaId));
	}

	@Mock
	private VotingSessionRepositoryTest votingSessionRepositoryTest;

	@InjectMocks
	private VotingSessionTestService votingSessionTestService;

	@Test
	void testCreateVotingSession_Success() {
		PautaTest pautaTest = new PautaTest(1L, "Pauta Test", LocalDateTime.now());
		VotingSessionDtoTest dto = new VotingSessionDtoTest();
		dto.setId(pautaTest.getId());
		dto.setPautaId(1);
		dto.setDataCriacao(LocalDateTime.now()); // Definindo a data de criação
		dto.setDataFinal(LocalDateTime.now().plusHours(1)); // Definindo uma data de encerramento
		when(pautaRepositoryTest.findById(pautaTest.getId())).thenReturn(Optional.of(pautaTest));
		when(votingSessionRepositoryTest.existsById(any(Long.class))).thenReturn(false);
		when(votingSessionRepositoryTest.save(any(VotingSessionTest.class))).thenAnswer(i -> i.getArguments()[0]);
		VotingSessionDtoTest created = votingSessionTestService.create(dto);

		assertNotNull(created);
	}

	@Test
	void testCreateVotingSession_Duplicate() {
		PautaTest pautaTest = new PautaTest(1L, "Pauta Test", LocalDateTime.now());
		VotingSessionDtoTest dto = new VotingSessionDtoTest();
		dto.setId(pautaTest.getId());
		dto.setPautaId(1);
		dto.setDataCriacao(LocalDateTime.now()); // Definindo a data de criação
		dto.setDataFinal(LocalDateTime.now().plusHours(1)); // Definindo uma data de encerramento
		when(pautaRepositoryTest.findById(pautaTest.getId())).thenReturn(Optional.of(pautaTest));
		when(votingSessionRepositoryTest.existsById(dto.getId())).thenReturn(true);

		assertThrows(DuplicateVotingSessionException.class, () -> votingSessionTestService.create(dto));
	}

}
