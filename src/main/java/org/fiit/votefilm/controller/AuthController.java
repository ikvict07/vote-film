package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login/")
    public String login(HttpServletRequest request, @RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            authenticationService.loginUser(username, password);
            redirectAttributes.addFlashAttribute("error", "You are logged in");
        } catch (AuthenticationFailedException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }

        return "redirect:/voting/enter/";
    }

    @GetMapping("/login/")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/register/")
    public String register(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            authenticationService.registerUser(username, password);
        } catch (AuthenticationFailedException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/login/";
        }
        return "redirect:/auth/login/";
    }

    @GetMapping("/logout/")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login/";
    }
}
