package org.fiit.votefilm.service;


import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.exceptions.InvalidSessionIdException;
import org.fiit.votefilm.exceptions.NotEnoughPoints;
import org.fiit.votefilm.model.*;
import org.fiit.votefilm.repository.*;
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

    private final TMDBFilmRepository tmdbFilmRepository;
    private final OMDBFilmRepository omdbFilmRepository;
    private final FindFilmService findFilmService;
    private final FilmFactory filmFactory;

    public VotingLogic(VotingItemRepository votingItemRepository, VotingSessionRepository votingSessionRepository, VoterUserRepository voterUserRepository, TMDBFilmRepository tmdbFilmRepository, OMDBFilmRepository omdbFilmRepository, FindFilmService findFilmService, FilmFactory filmFactory) {
        this.votingItemRepository = votingItemRepository;
        this.votingSessionRepository = votingSessionRepository;
        this.voterUserRepository = voterUserRepository;
        this.tmdbFilmRepository = tmdbFilmRepository;
        this.omdbFilmRepository = omdbFilmRepository;
        this.findFilmService = findFilmService;
        this.filmFactory = filmFactory;
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


        Optional<? extends Film> ApiFilmOptional = filmFactory.getFilm(title);
        if (ApiFilmOptional.isPresent()) {
            Film film = ApiFilmOptional.get();

            if (film instanceof OMDBFilm omdbFilm) {
                votingItemRepository.findVotingItemByTitle(title).get().setFilm(omdbFilmRepository.findByTitle(title).get());
            } else if (film instanceof TMDBFilm tmdbFilm) {
                votingItemRepository.findVotingItemByTitle(title).get().setFilm(tmdbFilmRepository.findByTitle(title).get());
            }
        }

        user.setPoints(user.getPoints() - votes);
        voterUserRepository.save(user);
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
