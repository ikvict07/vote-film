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

/**
 * Controller for the authentication page.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Handles POST requests for user login.
     *
     * @param request            the HttpServletRequest object
     * @param username           the username of the user
     * @param password           the password of the user
     * @param redirectAttributes the RedirectAttributes object
     * @return a redirect URL depending on the outcome of the operation
     */
    @PostMapping("/login/")
    public String login(HttpServletRequest request, @RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            authenticationService.loginUser(username, password);
        } catch (AuthenticationFailedException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }

        return "redirect:/voting/enter/";
    }

    /**
     * Handles GET requests to the login form.
     *
     * @return the login view
     */

    @GetMapping("/login/")
    public String loginForm() {
        return "login";
    }

    /**
     * Handles POST requests for user registration.
     *
     * @param username           the username of the new user
     * @param password           the password of the new user
     * @param redirectAttributes the RedirectAttributes object
     * @return a redirect URL depending on the outcome of the operation
     */
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

    /**
     * Handles GET requests for user logout.
     *
     * @param session the HttpSession object
     * @return a redirect URL to the login page
     */
    @GetMapping("/logout/")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login/";
    }
}
