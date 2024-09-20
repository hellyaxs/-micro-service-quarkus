package domain;

import api.dto.in.CreateCandidate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Optional;
import java.util.UUID;

public record Candidate(String id,
                        Optional<String> photo,
                        String givenName, String familyName,
                        Optional<String> jobTitle,
                        String email,
                        Optional<String> phoneNumber) {

    public static Candidate toCreateCandidate(Optional<String> photo,
                                                    String givenName, String familyName,
                                                    Optional<String> jobTitle,
                                                    String email,
                                                    Optional<String> phoneNumber) {
        return new Candidate(UUID.randomUUID().toString(), photo, givenName, familyName, jobTitle, email, phoneNumber);
    }
}
