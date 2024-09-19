package infra.repositories;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;
import infra.repositories.entities.CandidateEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class SQLCandidateRespository  implements CandidateRepository {

    private final EntityManager entityManager;

    public SQLCandidateRespository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(List<Candidate> candidateList) {
        candidateList.stream()
                .map(CandidateEntity::fromDomain)
                .forEach(entityManager::merge);
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(CandidateEntity.class);
        var root = cq.from(CandidateEntity.class);
        cq.select(root).where(conditions(query, cb, root));
        return entityManager.createQuery(cq).getResultList().stream()
                .map(CandidateEntity::toDomain)
                .toList();
    }

    private Predicate[] conditions(CandidateQuery query,
                                   CriteriaBuilder cb,
                                   Root<CandidateEntity> root) {
        return Stream.of(query.ids().map(id -> cb.in(root.get("id")).value(id)),
                        query.name().map(name -> cb.or(cb.like(cb.lower(root.get("familyName")), name.stream().map(String::toLowerCase) + "%"),
                                cb.like(cb.lower(root.get("givenName")), name.stream().map(String::toLowerCase) + "%"))))
                .flatMap(Optional::stream)
                .toArray(Predicate[]::new);
    }

}
