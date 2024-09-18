package infra.repositories;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;
import infra.repositories.entities.CandidateEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

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
        var  cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(CandidateEntity.class);
        var root = cq.from(CandidateEntity.class);
        var where = query.ids().map(id -> cb.in(root.get("id")).value(id)).get();
        cq.select(root).where(where);
        return entityManager.createQuery(cq).getResultList().stream()
                .map(CandidateEntity::toDomain)
                .toList();
    }

}
