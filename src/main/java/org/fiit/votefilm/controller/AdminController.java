package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.fiit.votefilm.exceptions.AccessNotAllowed;
import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.exceptions.UserAlreadyRegisteredException;
import org.fiit.votefilm.repository.users.VoterUserRepository;
import org.fiit.votefilm.service.AuthenticationService;
import org.fiit.votefilm.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for the admin page.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AuthenticationService authenticationService;
    private final VoterUserRepository userRepository;

    private final PointsService pointsService;

    public AdminController(AuthenticationService authenticationService, VoterUserRepository userRepository, PointsService pointsService) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.pointsService = pointsService;
    }

    /**
     * Handles GET requests to the admin page.
     *
     * @return the admin view
     */
    @GetMapping("/")
    public String admin() {
        return "admin";
    }

    /**
     * Handles POST requests to add a super user.
     *
     * @param request  the HttpServletRequest object
     * @param username the username of the new super user
     * @param password the password of the new super user
     * @return a redirect URL depending on the outcome of the operation
     * @throws UserAlreadyRegisteredException if a user with the given username already exists
     */
    @PostMapping("/add-super-user/")
    public String addSuperUser(HttpServletRequest request, @RequestParam String username, @RequestParam String password) throws UserAlreadyRegisteredException {
        try {
            authenticationService.addVotingHost(username, password);
        } catch (AccessNotAllowed e) {
            return "redirect:/";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    /**
     * Handles GET requests to the add super user form.
     *
     * @return add super user form view
     */
    @GetMapping("/add-super-user/")
    public String addSuperUserForm() {
        return "admin-add-super";
    }

    /**
     * Handles POST requests to set points for a user.
     *
     * @param username the username of the user
     * @param points   the new points value
     * @param request  the HttpServletRequest object
     * @return a redirect URL depending on the outcome of the operation
     */

    @PostMapping("/set-points/")
    public String setPoints(@RequestParam String username, @RequestParam Long points, @Autowired HttpServletRequest request) {
        try {
            pointsService.setPoints(username, points);
        } catch (AccessNotAllowed e) {
            return "redirect:/";
        } catch (AuthenticationFailedException e) {
            return "redirect:/auth/login/";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    /**
     * Handles GET requests to view all users.
     *
     * @param model the Model object
     * @return the users view
     */
    @GetMapping("/users/")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }

    @GetMapping("/create-admin-user/")
    public void createAdminUser() throws UserAlreadyRegisteredException {
        System.out.println("Creating admin user");
        authenticationService.addAdminWithoutPermission("admin", "admin");
    }

    @GetMapping("/create-host-user/")
    public void createVotingHost() throws UserAlreadyRegisteredException {
        System.out.println("Creating host user");
        authenticationService.addHostWithoutPermission("host", "host");
    }
}
