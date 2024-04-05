package org.fiit.votefilm.service;

import org.fiit.votefilm.enums.Role;
import org.fiit.votefilm.exceptions.AccessNotAllowed;
import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.model.users.VoterUser;
import org.fiit.votefilm.repository.users.VoterUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Service for points.
 */
@Service
public class PointsService {

    private final VoterUserRepository userRepository;

    public PointsService(VoterUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Add points to a user.
     *
     * @param username Username of the user.
     * @param points   Points to add.
     * @throws AccessNotAllowed              If the user is not allowed to add points.
     * @throws AuthenticationFailedException If the user does not exist.
     */

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

    /**
     * Get points of a user.
     *
     * @param username Username of the user.
     * @return Points of the user.
     * @throws AuthenticationFailedException If the user does not exist.
     */
    public Long getPoints(String username) throws AuthenticationFailedException {
        Optional<VoterUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new AuthenticationFailedException("User does not exist");
        }

        return user.get().getPoints();
    }

    /**
     * Remove points from a user.
     *
     * @param username Username of the user.
     * @param points   Points to remove.
     * @throws AccessNotAllowed If the user is not allowed to remove points.
     * @throws AuthenticationFailedException If the user does not exist.
     */
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

    /**
     * Set points of a user.
     *
     * @param username Username of the user.
     * @param points   Points to set.
     * @throws AccessNotAllowed If the user is not allowed to set points.
     * @throws AuthenticationFailedException If the user does not exist.
     */
    public void setPoints(String username, Long points) throws AccessNotAllowed, AuthenticationFailedException {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isAdmin = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.ADMIN.getRole()::equals);

        if (!isAdmin) {
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
