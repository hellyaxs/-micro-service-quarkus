package infra.lifecycle;

import application.Election;
import infra.respository.ResdisElectionRepository;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.logging.Logger;

@Startup
@ApplicationScoped
public class Subscribe {

    private static final Logger LOGGER = Logger.getLogger(Subscribe.class.getName());



    public Subscribe(ReactiveRedisDataSource dataSource, ResdisElectionRepository repository) {
        LOGGER.info("Startup: Subscribe");

        dataSource.pubsub(String.class)
                .subscribe("elections")
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .subscribe()
                .with(id -> {
                    LOGGER.info("Election " + id + " received from subscription");
                    LOGGER.info("Election " + repository.findById(id) + " starting");
                });
    }

}
