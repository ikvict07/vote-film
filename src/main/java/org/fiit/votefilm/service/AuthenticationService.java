package org.fiit.votefilm.service;

import jakarta.servlet.http.HttpSession;
import org.fiit.votefilm.exceptions.UserAlreadyRegisteredException;
import org.fiit.votefilm.model.VoterUser;
import org.fiit.votefilm.repository.VoterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Enumeration;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final VoterUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    private final UserServiceImpl userService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, VoterUserRepository userRepository, PasswordEncoder passwordEncoder, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
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
}
