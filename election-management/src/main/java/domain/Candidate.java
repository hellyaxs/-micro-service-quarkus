package domain;

import java.util.Optional;

public record Candidate(String id,
                        Optional<String> photo,
                        String givenName, String familyName,
                        Optional<String> jobTitle,
                        String email,
                        Optional<String> phoneNumber,
                        String birthDate) {
}
