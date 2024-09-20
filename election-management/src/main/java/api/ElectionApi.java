package api;

import domain.ElectionService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ElectionApi {


    private final ElectionService electionService;

    public ElectionApi(ElectionService electionService) {
        this.electionService = electionService;
    }

    public void submit(){
        electionService.submit();
    }
}
