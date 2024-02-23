package org.fiit.votefilm.service;


import org.fiit.votefilm.model.VotingItem;
import org.fiit.votefilm.model.VotingSession;
import org.fiit.votefilm.repository.VotingItemRepository;
import org.fiit.votefilm.repository.VotingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotingLogic {
    private final VotingItemRepository votingItemRepository;
    private final VotingSessionRepository votingSessionRepository;

    public VotingLogic(VotingItemRepository votingItemRepository, VotingSessionRepository votingSessionRepository) {
        this.votingItemRepository = votingItemRepository;
        this.votingSessionRepository = votingSessionRepository;
    }

    public void vote(String title, String sessionId, Long votes) {
        Optional<VotingSession> votingSession = votingSessionRepository.findByUniqueCode(sessionId);

        if (votingSession.isEmpty()) {
            throw new IllegalArgumentException("Voting session not found");
        }

        Optional<VotingItem> item = votingSession.get().getVotingItems().stream()
                .filter(votingItem -> votingItem.getTitle().equals(title))
                .findFirst();

        if (item.isEmpty()) {
            VotingItem votingItem = new VotingItem();
            votingItem.setTitle(title);
            votingItem.setVotes(votes);
            votingItem.setVotingSession(votingSession.get());
            votingItemRepository.save(votingItem);
        }
        else {
            item.get().setVotes(item.get().getVotes() + votes);
            votingItemRepository.save(item.get());
        }
    }

    public List<VotingItem> getVotingItems(String sessionId) {
        Optional<VotingSession> votingSession = votingSessionRepository.findByUniqueCode(sessionId);

        if (votingSession.isEmpty()) {
            throw new IllegalArgumentException("Voting session not found");
        }

        return votingSession.get().getVotingItems();
    }

    public VotingSession getVotingSession(String sessionId) {
        Optional<VotingSession> votingSession = votingSessionRepository.findByUniqueCode(sessionId);

        if (votingSession.isEmpty()) {
            throw new IllegalArgumentException("Voting session not found");
        }

        return votingSession.get();
    }
}
