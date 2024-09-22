package infra.scheduler;


import infra.repositories.RedisElectionRepository;
import infra.repositories.SQLElectionRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Sync {

    private final SQLElectionRepository sqlElectionRepository;
    private final RedisElectionRepository repository;


    public Sync(SQLElectionRepository sqlElectionRepository, RedisElectionRepository repository) {
        this.sqlElectionRepository = sqlElectionRepository;
        this.repository = repository;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    void syncWorker() {
        sqlElectionRepository.findAll().forEach(election -> sqlElectionRepository.sync(repository.sync(election)));
    }
}
