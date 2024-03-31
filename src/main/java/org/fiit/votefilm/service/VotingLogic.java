package org.fiit.votefilm.service;


import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.exceptions.InvalidSessionIdException;
import org.fiit.votefilm.exceptions.NotEnoughPoints;
import org.fiit.votefilm.model.VotingItem;
import org.fiit.votefilm.model.VotingSession;
import org.fiit.votefilm.model.apiFilm.Film;
import org.fiit.votefilm.model.apiFilm.OMDBFilm;
import org.fiit.votefilm.model.users.VoterUser;
import org.fiit.votefilm.repository.VotingItemRepository;
import org.fiit.votefilm.repository.VotingSessionRepository;
import org.fiit.votefilm.repository.apiFilm.OMDBFilmRepository;
import org.fiit.votefilm.repository.apiFilm.TMDBFilmRepository;
import org.fiit.votefilm.repository.users.VoterUserRepository;
import org.fiit.votefilm.service.apiFilm.FilmFactory;
import org.fiit.votefilm.service.apiFilm.FindFilmService;
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
                .orElseGet(() -> createNewVotingItem(ApiFilmOptional, title, votingSession));

        votingItem.setVotes(votingItem.getVotes() + votes);
        votingItemRepository.save(votingItem);

        user.setPoints(user.getPoints() - votes);
        voterUserRepository.save(user);
    }

    private VotingItem createNewVotingItem(Optional<? extends Film> film, String title, VotingSession votingSession) {
        VotingItem votingItem = new VotingItem();
        votingItem.setTitle(title);
        votingItem.setVotes(0L);
        votingItem.setVotingSession(votingSession);
        if (!film.isPresent()) {
            votingItem.setFilm(film.get() instanceof OMDBFilm ? omdbFilmRepository.findByTitle(title).get() : tmdbFilmRepository.findByTitle(title).get()); // TODO: findByTitle can return null
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
