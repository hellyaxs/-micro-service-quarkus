package api.dto.in;

import domain.Candidate;

import java.util.Optional;

public record UpdateCandidate(Optional<String> photo,
                              String givenName,
                              String familyName,
                              String jobTitle,
                              String email,
                              String phoneNumber) {


    public Candidate toDomain(String id) {
        return  new Candidate(id, photo, givenName, familyName, Optional.of(jobTitle), email, Optional.of(phoneNumber));
    }
}
