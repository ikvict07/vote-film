package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.VotingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for voting items.
 */
@Repository
public interface VotingItemRepository extends JpaRepository<VotingItem, Long> {
    /**
     * Find a voting item by title.
     *
     * @param title Title of the voting item.
     * @return Voting item with the given title.
     */
    Optional<VotingItem> findVotingItemByTitle(String title);
}
