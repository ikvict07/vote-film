package org.fiit.votefilm.controller;

import org.fiit.votefilm.DTO.VotePost;
import org.fiit.votefilm.service.VotingLogic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VotingController {

    private final VotingLogic votingLogic;

    public VotingController(VotingLogic votingLogic) {
        this.votingLogic = votingLogic;
    }

    @GetMapping("/vote")
    public String vote() {
        return "vote";
    }

    @PostMapping("/vote")
    public String voteSubmit(@RequestParam String title) {
        VotePost votePost = new VotePost();
        votePost.setTitle(title);
        votingLogic.vote(votePost);
        System.out.println("Voted for ");
        return "vote";
    }
}
