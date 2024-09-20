package infra.repositories.entities;

import domain.Candidate;
import domain.Election;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity(name = "election_candidates")
public class ElectionCandidateEntity {

    @EmbeddedId
    private ElectionCandidateId id;

    private Integer votes;

    public ElectionCandidateId getId() {
        return id;
    }

    public void setId(ElectionCandidateId id) {
        this.id = id;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public static ElectionCandidateEntity fromDomain(Election election, Candidate candidate, Integer votes){
        ElectionCandidateEntity electionCandidateEntity = new ElectionCandidateEntity();
        ElectionCandidateId electionCandidateId = new ElectionCandidateId();
        electionCandidateId.setElectionId(election.id());
        electionCandidateId.setCandidateId(candidate.id());
        electionCandidateEntity.setId(electionCandidateId);
        electionCandidateEntity.setVotes(votes);
        return electionCandidateEntity;
    }
}
