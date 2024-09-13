package infra.repositories;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SQLCandidateRespository  implements CandidateRepository {

    @Override
    public void save(List<Candidate> candidateList) {
        
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        return List.of();
    }

}
