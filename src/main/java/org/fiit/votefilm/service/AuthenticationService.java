package org.fiit.votefilm.service;

import org.fiit.votefilm.exceptions.UserAlreadyRegisteredException;
import org.fiit.votefilm.model.SuperUser;
import org.fiit.votefilm.model.VoterUser;
import org.fiit.votefilm.repository.SuperUserRepository;
import org.fiit.votefilm.repository.VoterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final VoterUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final SuperUserRepository superUserRepository;


    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, VoterUserRepository userRepository, PasswordEncoder passwordEncoder, SuperUserRepository superUserRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.superUserRepository = superUserRepository;
    }

    public boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public void loginUser(String username, String password) {
        if (userRepository.findByUsername(username).isEmpty()) {
            System.out.println("User does not exist");
            return;
        } else {
            if (!passwordEncoder.matches(password, userRepository.findByUsername(username).get().getPassword())) {
                System.out.println("Password is wrong");
                return;
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication authenticatedToken = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return;
        }

        System.out.println("user is:" + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println("Role is:" + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }

    public void logoutUser() {
        SecurityContextHolder.clearContext();
    }

    public void registerUser(String username, String password) throws UserAlreadyRegisteredException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyRegisteredException("User already exists");
        }
        userRepository.save(new VoterUser(username, passwordEncoder.encode(password)));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public void addSuperUser(String username, String password) throws UserAlreadyRegisteredException {
        if (superUserRepository.findSuperUserByUsername(username).isPresent()) {
            throw new UserAlreadyRegisteredException("User already exists");
        }
        superUserRepository.save(new SuperUser(username, passwordEncoder.encode(password)));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
