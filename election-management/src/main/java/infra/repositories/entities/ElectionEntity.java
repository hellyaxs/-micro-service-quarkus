package infra.repositories.entities;

import domain.Election;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name= "elections")
public class ElectionEntity {


    @Id
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public static ElectionEntity fromDomain(Election election){
        ElectionEntity electionEntity = new ElectionEntity();
        electionEntity.setId(election.id());
        return electionEntity;
    }
}
