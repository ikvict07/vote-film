package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.fiit.votefilm.service.VotingLogic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VotingController {

    private final VotingLogic votingLogic;

    public VotingController(VotingLogic votingLogic) {
        this.votingLogic = votingLogic;
    }

    @PostMapping("/vote/")
    public String voteSubmit(@RequestParam String title, @RequestParam String sessionId, @RequestParam Long votes, HttpServletRequest request) {
        votingLogic.vote(title, sessionId, votes);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
