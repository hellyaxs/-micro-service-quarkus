package domain;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CandidateRepositoryTest {

    public abstract CandidateRepository repository();

    @Test
    void save(){
        Candidate candidate = Instancio.create(Candidate.class);
        repository().save(candidate);

        repository().findById(candidate.id()).ifPresentOrElse(
            c -> assertEquals(candidate, c),
            () -> fail("Candidate not found")
        );
    }




}