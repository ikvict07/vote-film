package org.fiit.votefilm.repository.users;

import org.fiit.votefilm.model.users.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for users.
 */
@Repository
public interface UserRepository extends JpaRepository<AbstractUser, Long> {
    /**
     * Find a user by its username.
     *
     * @param username The username of the user.
     * @return The optional user with the given username.
     */
    Optional<AbstractUser> findByUsername(String username);

    /**
     * Check if a user with the given username exists.
     *
     * @param username The username of the user.
     * @return True if a user with the given username exists, false otherwise.
     */
    Boolean existsByUsername(String username);
}
