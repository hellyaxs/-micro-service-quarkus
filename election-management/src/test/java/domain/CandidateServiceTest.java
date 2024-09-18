package domain;


import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class CandidateServiceTest {


    @jakarta.inject.Inject
    CandidateService candidateService;

    @InjectMock
    CandidateRepository candidateRepository;

    @Test
    @Transactional
    void save() {
        Candidate candidate = Instancio.create(Candidate.class);

        candidateService.save(candidate);

        verify(candidateRepository).save(candidate);
        verifyNoMoreInteractions(candidateRepository);


    }

    @Test
    void findAll(){
        List<Candidate> candidates = Instancio.stream(Candidate.class).limit(10).toList();

        when(candidateRepository.findAll()).thenReturn(candidates);  // Mocking the repository

        List<Candidate> result =  candidateService.findAll();

        verify(candidateRepository).findAll();
        verifyNoMoreInteractions(candidateRepository);

        assertEquals(candidates, result);
    }

    @Test
    void findById_whenCandidateIsFound_returnsCandidate(){
            Candidate candidate = Instancio.create(Candidate.class);

            when(candidateRepository.findById(candidate.id())).thenReturn(Optional.of(candidate));

            Candidate result = candidateService.findById(candidate.id());
            verify(candidateRepository).findById(candidate.id());
            verifyNoMoreInteractions(candidateRepository);
            assertEquals(candidate, result);
    }

    @Test
    void findById_whenCandidateIsNotFound_throwsException(){
        Candidate candidate = Instancio.create(Candidate.class);

        when(candidateRepository.findById(candidate.id())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,() -> candidateService.findById(candidate.id()));

        verify(candidateRepository).findById(candidate.id());
        verifyNoMoreInteractions(candidateRepository);
    }

}