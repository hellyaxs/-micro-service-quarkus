package api.dto.in;

import domain.Candidate;

import java.util.Optional;

public record CreateCandidate(Optional<String> photo,
                              String givenName,
                              String familyName,
                              String jobTitle,
                              String email,
                              String phoneNumber) {

    public Candidate  toDomain() {
        return Candidate.toCreateCandidate(photo, givenName, familyName, Optional.of(jobTitle), email, Optional.of(phoneNumber));
    }


}
