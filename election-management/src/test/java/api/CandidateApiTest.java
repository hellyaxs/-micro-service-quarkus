package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.CandidateDTOout;
import domain.Candidate;
import domain.CandidateService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class CandidateApiTest {

    @Inject
    CandidateApi candidateApi;

    @InjectMock
    CandidateService candidateService;


    @Test
    void create(){
        CreateCandidate dto = Instancio.create(CreateCandidate.class);
        ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);

        candidateApi.create(dto);
        verify(candidateService).save(captor.capture());
        verifyNoMoreInteractions(candidateService);

        Candidate candidateCaptor = captor.getValue();

        assertEquals(candidateCaptor.familyName(), dto.familyName());
        assertEquals(candidateCaptor.givenName(), dto.givenName());
        assertEquals(candidateCaptor.email(), dto.email());
//        assertEquals(candidateCaptor.jobTitle(), dto.jobTitle());
//        assertEquals(candidateCaptor.phoneNumber(), dto.phoneNumber());
//        assertEquals(candidateCaptor.photo(), dto.photo().orElse(null));
    }

    @Test
    void update() {
        String id = UUID.randomUUID().toString();
        UpdateCandidate dto = Instancio.create(UpdateCandidate.class);
        Candidate candidate = dto.toDomain(id);

        ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);

        when(candidateService.findById(id)).thenReturn(candidate);

        var response = candidateApi.update(id, dto);

        verify(candidateService).save(captor.capture());
        verify(candidateService).findById(id);
        verifyNoMoreInteractions(candidateService);

        assertEquals(CandidateDTOout.fromDomain(candidate), response);
    }

    @Test
    void list() {
        var candidates = Instancio.stream(Candidate.class).limit(10).toList();
        when(candidateService.findAll()).thenReturn(candidates);
        var response = candidateApi.list();

        verify(candidateService).findAll();
        verifyNoMoreInteractions(candidateService);
        List<CandidateDTOout> dataCompare = candidates.stream().map(CandidateDTOout::fromDomain).toList();
        assertEquals(dataCompare, response);
    }

}