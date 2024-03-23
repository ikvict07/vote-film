package org.fiit.votefilm.service;


import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.exceptions.InvalidSessionIdException;
import org.fiit.votefilm.exceptions.NotEnoughPoints;
import org.fiit.votefilm.model.*;
import org.fiit.votefilm.repository.*;
import org.fiit.votefilm.service.filmApi.FilmFactory;
import org.fiit.votefilm.service.filmApi.FindFilmService;
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        VoterUser user = voterUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new AuthenticationFailedException("User not found"));

        if (user.getPoints() < votes) {
            throw new NotEnoughPoints("Not enough points");
        }

        VotingSession votingSession = votingSessionRepository.findByUniqueCode(sessionId)
                .orElseThrow(() -> new InvalidSessionIdException("Voting session not found"));

        Optional<? extends Film> ApiFilmOptional = filmFactory.getFilm(title);
        VotingItem votingItem = votingSession.getVotingItems().stream()
                .filter(vi -> vi.getTitle().equals(title))
                .findFirst()
                .orElseGet(() -> createOrFindVotingItem(ApiFilmOptional, title, votingSession));

        votingItem.setVotes(votingItem.getVotes() + votes);
        votingItemRepository.save(votingItem);

        user.setPoints(user.getPoints() - votes);
        voterUserRepository.save(user);
    }

    private VotingItem createOrFindVotingItem(Optional<? extends Film> ApiFilmOptional, String title, VotingSession votingSession) {
        if (ApiFilmOptional.isPresent()) {
            Film film = ApiFilmOptional.get();
            String filmTitle = film.getTitle();
            return votingItemRepository.findVotingItemByTitle(filmTitle)
                    .orElseGet(() -> createNewVotingItem(film, filmTitle, votingSession));
        } else {
            return createNewVotingItem(null, title, votingSession);
        }
    }

    private VotingItem createNewVotingItem(Film film, String title, VotingSession votingSession) {
        VotingItem votingItem = new VotingItem();
        votingItem.setTitle(title);
        votingItem.setVotes(0L);
        votingItem.setVotingSession(votingSession);
        if (film != null) {
            votingItem.setFilm(film instanceof OMDBFilm ? omdbFilmRepository.findByTitle(title).get() : tmdbFilmRepository.findByTitle(title).get());
        }
        return votingItemRepository.save(votingItem);
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
