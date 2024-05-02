package org.fiit.votefilm.repository.users;

import org.fiit.votefilm.model.users.VotingHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Voting hosts
 */
@Repository
public interface VotingHostRepository extends JpaRepository<VotingHost, Long> {
    /**
     * Find a Voting host by username.
     *
     * @param username Username of the Voting host
     * @return Voting host with the given username.
     */
    Optional<VotingHost> findVotingHostByUsername(String username);

    default boolean existsByUsername(String username) {
        return findVotingHostByUsername(username).isPresent();
    }

}
