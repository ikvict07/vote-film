package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.VoterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoterUserRepository extends JpaRepository<VoterUser, Long> {
    Optional<VoterUser> findByUsername(String username);

    Boolean existsByUsername(String username);

}
