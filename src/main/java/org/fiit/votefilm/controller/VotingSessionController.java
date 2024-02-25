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

@Controller
@RequestMapping("/voting-setup")
public class VotingSessionController {

    private final VotingService votingService;

    public VotingSessionController(VotingService votingService) {
        this.votingService = votingService;
    }

    @GetMapping("/start/")
    public String getStartPage() {
        return "voting-start";
    }

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
