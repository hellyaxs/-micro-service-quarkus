package infra.lifecycle;


import infra.respository.ResdisElectionRepository;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.logging.Logger;

@Startup
@ApplicationScoped
public class Cache {

    private static final Logger LOGGER = Logger.getLogger(Cache.class.getName());

    public Cache(ResdisElectionRepository repository) {
        LOGGER.info("Startup: Cache");
        repository.findAll();
    }
}
