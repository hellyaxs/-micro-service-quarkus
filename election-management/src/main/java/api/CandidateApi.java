package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.CandidateDTOout;
import domain.Candidate;
import domain.CandidateService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CandidateApi {

    private final CandidateService candidateService;


    public CandidateApi(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    public void create(CreateCandidate createCandidate) {
        candidateService.save(createCandidate.toDomain());

    }

    public CandidateDTOout update(String id, UpdateCandidate dto) {
        candidateService.save(dto.toDomain(id));
        Candidate candidate = candidateService.findById(id);
        return  CandidateDTOout.fromDomain(candidate);
    }

    public List<CandidateDTOout> list() {
        return candidateService.findAll().stream().map(CandidateDTOout::fromDomain).toList();
    }
}
