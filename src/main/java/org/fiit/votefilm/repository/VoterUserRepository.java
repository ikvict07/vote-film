package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.VoterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for voter users.
 */
@Repository
public interface VoterUserRepository extends JpaRepository<VoterUser, Long> {
    /**
     * Find a voter user by username.
     *
     * @param username Username of the voter user.
     * @return Voter user with the given username.
     */
    Optional<VoterUser> findByUsername(String username);

    /**
     * Check if a voter user with the given username exists.
     *
     * @param username Username of the voter user.
     * @return True if a voter user with the given username exists, false otherwise.
     */

    Boolean existsByUsername(String username);

}