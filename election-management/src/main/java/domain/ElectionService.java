package domain;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;

@ApplicationScoped
public class ElectionService {
    private final CandidateService candidateService;
    private  final Instance<ElectionRepository> respositories;


    public ElectionService(CandidateService candidateService, Instance<ElectionRepository> respositories) {
        this.candidateService = candidateService;
        this.respositories = respositories;
    }

    public void submit(){
        Election election = Election.create(candidateService.findAll());
        respositories.forEach(respositories -> respositories.submit(election));
    }
}
