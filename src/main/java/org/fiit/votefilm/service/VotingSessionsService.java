package org.fiit.votefilm.service;

import org.fiit.votefilm.model.VotingSession;
import org.fiit.votefilm.model.users.VotingHost;
import org.fiit.votefilm.repository.VotingSessionRepository;
import org.fiit.votefilm.repository.users.VotingHostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotingSessionsService {
    private final VotingSessionRepository votingSessionRepository;
    private final VotingHostRepository votingHostRepository;

    public VotingSessionsService(VotingSessionRepository votingSessionRepository, VotingHostRepository votingHostRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.votingHostRepository = votingHostRepository;
    }

    public List<VotingSession> getAllVotingSessionsForHost() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        String name = auth.getName();
        Optional<VotingHost> user = votingHostRepository.findVotingHostByUsername(name);
        if (user.isEmpty()) {
            throw new AssertionError("User should be authenticated");
        }
        return votingSessionRepository.findVotingSessionsByCreator(user.get());
    }
}
