package infra.repositories.entities;


import domain.Candidate;
import jakarta.persistence.*;

import java.util.Optional;

@Entity(name = "candidates")
public class CandidateEntity {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    @Id
    @Column(name = "id")
    private String id;

    private String photo;
    @Column(name = "given_name")
    private String givenName;
    @Column(name = "family_name")
    private String familyName;
    @Column(name = "job_title")
    private String jobTitle;

    private String email;
    @Column(name = "phone")
    private String phoneNumber;



    public static CandidateEntity fromDomain(Candidate candidate) {
        CandidateEntity entity = new CandidateEntity();
        entity.setId(candidate.id());
        entity.setPhoto(candidate.photo().orElse(null));
        entity.setGivenName(candidate.givenName());
        entity.setFamilyName(candidate.familyName());
        entity.setJobTitle(candidate.jobTitle().orElse(null));
        entity.setEmail(candidate.email());
        entity.setPhoneNumber(candidate.phoneNumber().orElse(null));
        return entity;
    }


    public static Candidate toDomain(CandidateEntity entity) {
        return new Candidate(entity.getId(),
                Optional.ofNullable(entity.getPhoto()),
                entity.getGivenName(),
                entity.getFamilyName(),
                Optional.ofNullable(entity.getJobTitle()),
                entity.getEmail(),
                Optional.ofNullable(entity.getPhoneNumber()));

    }
}
