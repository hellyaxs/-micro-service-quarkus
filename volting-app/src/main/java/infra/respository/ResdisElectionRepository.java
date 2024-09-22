package infra.respository;

import application.Candidate;
import application.Election;
import application.ElectionRepository;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.cache.CacheResult;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ResdisElectionRepository implements ElectionRepository {

    private final SortedSetCommands<String, String> sortedSetCommands;
    private final KeyCommands<String> keyCommands;
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ResdisElectionRepository.class));

    public  ResdisElectionRepository(RedisDataSource redisDataSource){
        this.sortedSetCommands = redisDataSource.sortedSet(String.class, String.class);
         keyCommands = redisDataSource.key(String.class);
        LOGGER.info("Redis Election Repository");
    }

    @Override
    @CacheResult(cacheName = "memoization")
    public Election findById(String id) {

        List<Candidate> candidates = sortedSetCommands.zrange("elections"+id,0,-1 )
                .stream().map(Candidate::new).toList();
        return new Election(id,candidates);
    }

    @Override
    public List<Election> findAll() {
        return keyCommands.keys("election:*")
                .stream()
                 .map(id -> findById(id.replace("election:",""))).toList();
    }

    @Override
    public void vote(String electionId, Candidate candidate) {
        LOGGER.info("Vote for candidate " + candidate.id() + " in election " + electionId);
        sortedSetCommands.zincrby("election: "+electionId,1,candidate.id());
    }
}
