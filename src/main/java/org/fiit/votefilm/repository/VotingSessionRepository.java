package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for voting sessions.
 */
public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
    /**
     * Find a voting session by unique code.
     *
     * @param uniqueCode Unique code of the voting session.
     * @return Voting session with the given unique code.
     */
    Optional<VotingSession> findByUniqueCode(String uniqueCode);

}
