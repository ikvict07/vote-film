package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
    Optional<VotingSession> findByUniqueCode(String uniqueCode);

}
