package org.fiit.votefilm.controller;

import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.model.VotingSession;
import org.fiit.votefilm.service.VotingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for the voting setup page.
 */
@Controller
@RequestMapping("/voting-setup")
public class VotingSessionController {

    private final VotingService votingService;

    public VotingSessionController(VotingService votingService) {
        this.votingService = votingService;
    }

    /**
     * Handles GET requests to the voting-start page.
     *
     * @return the voting-start view
     */
    @GetMapping("/start/")
    public String getStartPage() {
        return "voting-start";
    }

    /**
     * Handles POST requests to start a voting session.
     *
     * @param title              the title of the voting session
     * @param redirectAttributes the RedirectAttributes object
     * @return a redirect URL depending on the outcome of the operation
     */
    @PostMapping("/start/submit/")
    public String startVotingSession(@RequestParam String title, RedirectAttributes redirectAttributes) {
        VotingSession votingSession;
        try {
            votingSession = votingService.startVotingSession(title);
        } catch (AuthenticationFailedException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/login/";
        }
        return "redirect:/voting/" + votingSession.getUniqueCode();
    }
}
