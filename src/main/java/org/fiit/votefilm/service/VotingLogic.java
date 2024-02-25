package org.fiit.votefilm.service;


import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.exceptions.InvalidSessionIdException;
import org.fiit.votefilm.exceptions.NotEnoughPoints;
import org.fiit.votefilm.model.VoterUser;
import org.fiit.votefilm.model.VotingItem;
import org.fiit.votefilm.model.VotingSession;
import org.fiit.votefilm.repository.VoterUserRepository;
import org.fiit.votefilm.repository.VotingItemRepository;
import org.fiit.votefilm.repository.VotingSessionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Logic for voting.
 */
@Service
public class VotingLogic {
    private final VotingItemRepository votingItemRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final VoterUserRepository voterUserRepository;

    public VotingLogic(VotingItemRepository votingItemRepository, VotingSessionRepository votingSessionRepository, VoterUserRepository voterUserRepository) {
        this.votingItemRepository = votingItemRepository;
        this.votingSessionRepository = votingSessionRepository;
        this.voterUserRepository = voterUserRepository;
    }

    /**
     * Vote for a voting item.
     *
     * @param title     Title of the voting item.
     * @param sessionId Session ID of the voting session.
     * @param votes     Votes to add.
     * @throws AuthenticationFailedException If the user is not found.
     * @throws NotEnoughPoints               If the user does not have enough points.
     * @throws InvalidSessionIdException     If the session ID is invalid.
     */
    public void vote(String title, String sessionId, Long votes) throws AuthenticationFailedException, NotEnoughPoints, InvalidSessionIdException {

        VoterUser user;

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = voterUserRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new AuthenticationFailedException("User not found"));

        if (user.getPoints() < votes) {
            throw new NotEnoughPoints("Not enough points");
        }

        Optional<VotingSession> votingSession = votingSessionRepository.findByUniqueCode(sessionId);

        if (votingSession.isEmpty()) {
            throw new InvalidSessionIdException("Voting session not found");
        }

        Optional<VotingItem> item = votingSession.get().getVotingItems().stream()
                .filter(votingItem -> votingItem.getTitle().equals(title))
                .findFirst();


        user.setPoints(user.getPoints() - votes);
        voterUserRepository.save(user);

        if (item.isEmpty()) {
            VotingItem votingItem = new VotingItem();
            votingItem.setTitle(title);
            votingItem.setVotes(votes);
            votingItem.setVotingSession(votingSession.get());
            votingItemRepository.save(votingItem);
        } else {
            item.get().setVotes(item.get().getVotes() + votes);
            votingItemRepository.save(item.get());
        }
    }

    /**
     * Get voting items of a voting session.
     *
     * @param sessionId Session ID of the voting session.
     * @return Voting items of the voting session.
     * @throws InvalidSessionIdException If the session ID is invalid.
     */
    public List<VotingItem> getVotingItems(String sessionId) throws InvalidSessionIdException {
        Optional<VotingSession> votingSession = votingSessionRepository.findByUniqueCode(sessionId);

        if (votingSession.isEmpty()) {
            throw new InvalidSessionIdException("Voting session not found");
        }

        return votingSession.get().getVotingItems();
    }

    /**
     * Get a voting session.
     *
     * @param sessionId Session ID of the voting session.
     * @return Voting session with the given session ID.
     * @throws InvalidSessionIdException If the session ID is invalid.
     */
    public VotingSession getVotingSession(String sessionId) throws InvalidSessionIdException {
        Optional<VotingSession> votingSession = votingSessionRepository.findByUniqueCode(sessionId);

        if (votingSession.isEmpty()) {
            throw new InvalidSessionIdException("Voting session not found");
        }

        return votingSession.get();
    }
}
