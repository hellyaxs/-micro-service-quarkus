package domain;


import domain.annotations.Principal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;

import java.util.List;

@ApplicationScoped
public class ElectionService {
    private final CandidateService candidateService;
    private  final Instance<ElectionRepository> respositories;
    private final ElectionRepository repository;


    public ElectionService(CandidateService candidateService, @Any Instance<ElectionRepository> respositories, @Principal ElectionRepository repository) {
        this.candidateService = candidateService;
        this.respositories = respositories;
        this.repository = repository;
    }

    public List<Election> findAll(){
        return repository.findAll();
    }

    public void submit(){
        Election election = Election.create(candidateService.findAll());
        respositories.forEach(respositories -> respositories.submit(election));
    }

}
