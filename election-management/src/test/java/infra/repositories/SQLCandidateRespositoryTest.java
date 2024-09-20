package infra.repositories;

import domain.CandidateRepository;
import domain.CandidateRepositoryTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;

@QuarkusTest
class SQLCandidateRespositoryTest extends CandidateRepositoryTest {

    @Inject
    EntityManager entityManager;

    @Inject
    SQLCandidateRespository respository;

    @Override
    public CandidateRepository repository() {
        return respository;
    }


    @AfterEach
    @Transactional
    void tearDown() {
        entityManager.createNativeQuery("TRUNCATE TABLE candidates").executeUpdate();
    }
}