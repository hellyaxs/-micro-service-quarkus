package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import domain.CandidateService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CandidateApi {

    private final CandidateService candidateService;


    public CandidateApi(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    public void create(CreateCandidate createCandidate) {
        candidateService.save(createCandidate.toDomain());

    }

    public void update(String id,UpdateCandidate dto) {
        candidateService.save(dto.toDomain(id));
    }
}
