package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpSession;
import org.fiit.votefilm.exceptions.UserAlreadyRegisteredException;
import org.fiit.votefilm.service.AuthenticationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Enumeration;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login/")
    public String login(@RequestParam String username, @RequestParam String password) {
        System.out.println("HEre");
        System.out.println("Logging in:" + username + " " + password);
        authenticationService.loginUser(username, password);

        return "login";
    }
    @GetMapping("/login/")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        try {
            authenticationService.registerUser(username, password);
        } catch (UserAlreadyRegisteredException e) {
            //TODO: handle exception
            System.out.println("Failed to register:" + username + " " + password);
            return "register";
        }
        System.out.println("Registered:" + username + " " + password);
        return "register";
    }

    @GetMapping("/addSuperUser")
    public void addSuperUser() throws UserAlreadyRegisteredException {
        authenticationService.addSuperUser("admin", "admin");
    }
}
