package domain;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Predicates.instanceOf;
import static org.instancio.Select.field;
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

    @Test
    void findAll(){
        List<Candidate> candidate = Instancio.stream(Candidate.class).limit(10).toList();
        repository().save(candidate);
        List<Candidate> results= repository().findAll();
        assertEquals(results.size(), candidate.size());
    }

    @Test
    void findByName(){
        Candidate candidate = Instancio.create(Candidate.class);
        Candidate candidate2 = Instancio.of(Candidate.class).set(field("familyName"),"John").create();
        CandidateQuery query = new CandidateQuery.Builder().name(Set.of("JO")).build();
        repository().save(List.of(candidate, candidate2));

        List<Candidate> result = repository().find(query);
        assertEquals(1, result.size());     // This test is failing
        assertEquals(candidate2, result.get(0));
    }




}