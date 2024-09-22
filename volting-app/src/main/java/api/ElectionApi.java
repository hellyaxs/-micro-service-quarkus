package api;


import api.dto.out.ElectionDto;
import application.Election;
import application.ElectionService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ElectionApi {
    private final ElectionService electionService;

    public ElectionApi(ElectionService electionService) {

        this.electionService = electionService;
    }

    public List<ElectionDto> findAll() {
        return electionService.findAll().stream().map(ElectionDto::fromDomain).toList();
    }

    public void vote(String electionId, String candidateId) {
          electionService.vote(electionId, candidateId);
    }
}
