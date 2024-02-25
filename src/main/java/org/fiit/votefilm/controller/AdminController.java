package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.fiit.votefilm.exceptions.UserAlreadyRegisteredException;
import org.fiit.votefilm.repository.VoterUserRepository;
import org.fiit.votefilm.service.AuthenticationService;
import org.fiit.votefilm.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/")
    public String admin() {
        return "admin";
    }

    @PostMapping("/addSuperUser/")
    public void addSuperUser() throws UserAlreadyRegisteredException {
        authenticationService.addSuperUser("admin", "admin");
    }

    @PostMapping("/set-points/")
    public String setPoints(@RequestParam String username, @RequestParam Long points, @Autowired HttpServletRequest request) {
        pointsService.setPoints(username, points);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/users/")
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }
}
