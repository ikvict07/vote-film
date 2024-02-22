package org.fiit.votefilm.service;

import org.fiit.votefilm.DTO.VotePost;
import org.fiit.votefilm.model.VotingItem;
import org.fiit.votefilm.repository.VotingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotingLogic {
    private final VotingItemRepository votingItemRepository;

    public VotingLogic(VotingItemRepository votingItemRepository) {
        this.votingItemRepository = votingItemRepository;
    }

    public void vote(VotePost request) {
        Optional<VotingItem> votingItem = votingItemRepository.findVotingItemByTitle(request.getTitle());

        if (votingItem.isPresent()) {
            VotingItem item = votingItem.get();
            item.setVotes(item.getVotes() + 1);
            votingItemRepository.save(item);
        }
        else {
            VotingItem item = new VotingItem();
            item.setTitle(request.getTitle());
            item.setVotes(1L);
            votingItemRepository.save(item);
        }

    }
}
