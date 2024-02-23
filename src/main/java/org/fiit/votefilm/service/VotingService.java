package org.fiit.votefilm.service;

import org.fiit.votefilm.model.SuperUser;
import org.fiit.votefilm.model.VotingSession;
import org.fiit.votefilm.repository.SuperUserRepository;
import org.fiit.votefilm.repository.VotingSessionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VotingService {
    private final VotingSessionRepository votingRepository;
    private final SuperUserRepository superUserRepository;

    public VotingService(VotingSessionRepository votingRepository, SuperUserRepository superUserRepository) {
        this.votingRepository = votingRepository;
        this.superUserRepository = superUserRepository;
    }

    public VotingSession startVotingSession(String title) {
        SuperUser superUser = superUserRepository.findSuperUserByUsername(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName())
                .orElseThrow(() -> new UsernameNotFoundException("No user exists with this username"));

        VotingSession votingSession = new VotingSession(superUser, title);
        return votingRepository.save(votingSession);
    }

}
