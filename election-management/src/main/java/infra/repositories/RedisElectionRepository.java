package infra.repositories;

import domain.Election;
import domain.ElectionRepository;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class RedisElectionRepository implements ElectionRepository {

    private final SortedSetCommands<String, String> comands;
    private final PubSubCommands<String> pubsubComands;

    public RedisElectionRepository(RedisDataSource redisDataSource) {
         comands = redisDataSource.sortedSet(String.class, String.class);
         pubsubComands = redisDataSource.pubsub(String.class);
    }
    @Override
    public void submit(Election election) {
        Map<String, Double> rank = election.votes()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().id(), entry -> entry.getValue().doubleValue()));

        comands.zadd("elections" +election.id(),rank);
        pubsubComands.publish("elections",election.id());
    }
}
