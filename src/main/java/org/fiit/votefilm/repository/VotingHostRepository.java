package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.users.VotingHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for super users. (Voting hosts)
 */
@Repository
public interface VotingHostRepository extends JpaRepository<VotingHost, Long> {
    /**
     * Find a super user by username. (Voting host)
     *
     * @param username Username of the super user. (Voting host)
     * @return Super user with the given username. (Voting host)
     */
    Optional<VotingHost> findVotingHostByUsername(String username);

}
