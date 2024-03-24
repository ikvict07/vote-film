package org.fiit.votefilm.service;

import org.fiit.votefilm.enums.Role;
import org.fiit.votefilm.exceptions.*;
import org.fiit.votefilm.model.users.Admin;
import org.fiit.votefilm.model.users.VoterUser;
import org.fiit.votefilm.model.users.VotingHost;
import org.fiit.votefilm.repository.AdminRepository;
import org.fiit.votefilm.repository.UserRepository;
import org.fiit.votefilm.repository.VotingHostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service for authentication.
 */
@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VotingHostRepository votingHostRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, VotingHostRepository superUserRepository, AdminRepository adminRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.votingHostRepository = superUserRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * Log in a user.
     *
     * @param username Username of the user.
     * @param password Password of the user.
     * @throws AuthenticationFailedException If the authentication failed.
     * @throws UserIsNotRegisteredException  If the user is not registered.
     * @throws WrongPasswordException        If the password is wrong.
     */
    public void loginUser(String username, String password) throws AuthenticationFailedException {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new UserIsNotRegisteredException("User does not exist");
        } else {
            if (!passwordEncoder.matches(password, userRepository.findByUsername(username).get().getPassword())) {
                throw new WrongPasswordException("Wrong password");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication authenticatedToken = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Authentication failed");
        }

    }

    /**
     * Register a user.
     *
     * @param username Username of the user.
     * @param password Password of the user.
     * @throws AuthenticationFailedException  If the authentication failed.
     * @throws UserAlreadyRegisteredException If the user is already registered.
     */
    public void registerUser(String username, String password) throws AuthenticationFailedException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyRegisteredException("User already exists");
        }
        userRepository.save(new VoterUser(username, passwordEncoder.encode(password)));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    /**
     * Add a super user. (Voting host)
     *
     * @param username Username of the super user. (Voting host)
     * @param password Password of the super user. (Voting host)
     * @throws UserAlreadyRegisteredException If the super user is already registered.
     * @throws AccessNotAllowed               If the user is not allowed to add a super user.
     */
    public void addVotingHost(String username, String password) throws UserAlreadyRegisteredException, AccessNotAllowed {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isVotingHost = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.VOTING_HOST.getRole()::equals);

        if (!isVotingHost) {
            throw new AccessNotAllowed("You are not allowed to add points");
        }

        if (votingHostRepository.findVotingHostByUsername(username).isPresent()) {
            throw new UserAlreadyRegisteredException("User already exists");
        }
        votingHostRepository.save(new VotingHost(username, passwordEncoder.encode(password)));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public void addAdminWithoutPermission(String username, String password) throws UserAlreadyRegisteredException {
        if (adminRepository.findAdminByUsername(username).isPresent()) {
            throw new UserAlreadyRegisteredException("User already exists");
        }
        adminRepository.save(new Admin(username, passwordEncoder.encode(password)));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public void addHostWithoutPermission(String username, String password) throws UserAlreadyRegisteredException {
        if (votingHostRepository.findVotingHostByUsername(username).isPresent()) {
            throw new UserAlreadyRegisteredException("User already exists");
        }
        votingHostRepository.save(new VotingHost(username, passwordEncoder.encode(password)));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
