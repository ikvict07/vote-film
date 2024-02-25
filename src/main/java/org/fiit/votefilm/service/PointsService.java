package org.fiit.votefilm.service;

import org.fiit.votefilm.enums.Role;
import org.fiit.votefilm.exceptions.AccessNotAllowed;
import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.model.VoterUser;
import org.fiit.votefilm.repository.VoterUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PointsService {

    private final VoterUserRepository userRepository;

    public PointsService(VoterUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addPoints(String username, int points) throws AccessNotAllowed, AuthenticationFailedException {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isVotingHost = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.VOTING_HOST.getRole()::equals);

        if (!isVotingHost) {
            throw new AccessNotAllowed("You are not allowed to add points");
        }


        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new AuthenticationFailedException("User does not exist");
        }

        user.get().setPoints(getPoints(username) + points);
        userRepository.save(user.get());
    }

    public Long getPoints(String username) throws AuthenticationFailedException {
        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new AuthenticationFailedException("User does not exist");
        }

        return user.get().getPoints();
    }

    public void removePoints(String username, int points) throws AccessNotAllowed, AuthenticationFailedException {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isVotingHost = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.VOTING_HOST.getRole()::equals);

        if (!isVotingHost) {
            throw new AccessNotAllowed("You are not allowed to remove points");
        }

        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new AuthenticationFailedException("User does not exist");
        }

        user.get().setPoints(getPoints(username) - points);
        userRepository.save(user.get());
    }

    public void setPoints(String username, Long points) throws AccessNotAllowed, AuthenticationFailedException {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isVotingHost = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.VOTING_HOST.getRole()::equals);

        if (!isVotingHost) {
            throw new AccessNotAllowed("You are not allowed to set points");
        }

        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new AuthenticationFailedException("User does not exist");
        }

        user.get().setPoints(points);
        userRepository.save(user.get());
    }
}
