package org.fiit.votefilm.controller;

import org.fiit.votefilm.model.VotingSession;
import org.fiit.votefilm.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/voting-setup")
public class VotingSessionController {

    @Autowired
    private VotingService votingService;
    @GetMapping("/start/")
    public String getStartPage() {
        return "voting-start";
    }

    @PostMapping("/start/submit/")
    public String startVotingSession(@RequestParam String title) {
        VotingSession votingSession = votingService.startVotingSession(title);
        return "redirect:/voting/" + votingSession.getUniqueCode();
    }
}
