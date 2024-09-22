package infra.repositories;

import domain.Candidate;
import domain.Election;
import domain.ElectionRepository;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.redis.datasource.sortedset.ScoreRange;
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

    public Election sync(Election election) {
        var map = comands.zrangebyscoreWithScores("election:" + election.id(),
                        ScoreRange.from(Integer.MIN_VALUE, Integer.MAX_VALUE))
                .stream()
                .map(scoredValue -> {
                    Candidate candidate = election.votes()
                            .keySet()
                            .stream()
                            .filter(c -> c.id().equals(scoredValue.value()))
                            .findFirst()
                            .orElseThrow();

                    return Map.entry(candidate, (int) scoredValue.score());
                })
                .toArray(Map.Entry[]::new);

        return new Election(election.id(), Map.ofEntries(map));
    }
}
