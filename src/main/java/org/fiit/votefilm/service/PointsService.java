package org.fiit.votefilm.service;

import org.fiit.votefilm.enums.Role;
import org.fiit.votefilm.model.VoterUser;
import org.fiit.votefilm.repository.VoterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

    public void addPoints(String username, int points) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isVotingHost = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.VOTING_HOST.getRole()::equals);

        if (!isVotingHost) {
            throw new AccessDeniedException("You are not allowed to add points");
        }


        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        user.get().setPoints(getPoints(username) + points);
        userRepository.save(user.get());
    }

    public Long getPoints(String username) {
        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        return user.get().getPoints();
    }

    public void removePoints(String username, int points) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isVotingHost = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.VOTING_HOST.getRole()::equals);

        if (!isVotingHost) {
            throw new AccessDeniedException("You are not allowed to remove points");
        }

        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        user.get().setPoints(getPoints(username) - points);
        userRepository.save(user.get());
    }

    public void setPoints(String username, Long points) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isVotingHost = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.VOTING_HOST.getRole()::equals);

        if (!isVotingHost) {
            throw new AccessDeniedException("You are not allowed to set points");
        }

        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        user.get().setPoints(points);
        userRepository.save(user.get());
    }
}
