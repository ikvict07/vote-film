package org.fiit.votefilm.repository.users;

import org.fiit.votefilm.model.users.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AbstractUser, Long> {
    Optional<AbstractUser> findByUsername(String username);

    Boolean existsByUsername(String username);
}
