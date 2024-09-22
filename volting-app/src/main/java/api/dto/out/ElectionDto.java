package api.dto.out;

import application.Candidate;
import application.Election;

import java.util.List;

public record ElectionDto(String id, List<String> candidates) {
    public static ElectionDto fromDomain(Election election) {
        return new ElectionDto(election.id(), election.candidates().stream().map(Candidate::id).toList());
    }
}
