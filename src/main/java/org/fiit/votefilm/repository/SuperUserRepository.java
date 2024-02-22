package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.SuperUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperUserRepository extends JpaRepository<SuperUser, Long> {
    Optional<SuperUser> findSuperUserByUsername(String username);

}
