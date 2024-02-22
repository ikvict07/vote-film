package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.VotingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotingItemRepository extends JpaRepository<VotingItem, Long> {
    Optional<VotingItem> findVotingItemByTitle(String title);
}
