package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpSession;
import org.fiit.votefilm.model.VotingItem;
import org.fiit.votefilm.model.VotingSession;
import org.fiit.votefilm.repository.VotingItemRepository;
import org.fiit.votefilm.repository.VotingSessionRepository;
import org.fiit.votefilm.service.VotingLogic;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RepresentationController {
    private final VotingItemRepository votingItemRepository;
    private final VotingLogic votingLogic;

    public RepresentationController(VotingItemRepository votingItemRepository, VotingLogic votingLogic) {
        this.votingItemRepository = votingItemRepository;
        this.votingLogic = votingLogic;
    }

    @GetMapping("/voting/")
    public String votingList(Model model, HttpSession session) {

        System.out.println("user is:" + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(System.out::println);
        System.out.println("session is:" + session.getAttribute("username"));
        model.addAttribute("votingItems", votingItemRepository.findAll());
        model.addAttribute("votes", votingItemRepository.findAll().stream().map(VotingItem::getVotes));
        return "voting";
    }

    @GetMapping("/voting/{id}")
    public String votingList(Model model, @PathVariable String id) {
        model.addAttribute("votingItems", votingLogic.getVotingItems(id));
        model.addAttribute("votes", votingLogic.getVotingItems(id).stream().map(VotingItem::getVotes));
        model.addAttribute("sessionId", id);
        return "voting";
    }

    @GetMapping("/voting/enter/")
    public String votingEnter() {
        System.out.println("user is:" + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "voting-enter";
    }

    @PostMapping("/voting/enter/")
    public String votingEnterSubmit(@RequestParam String sessionId) {
        return "redirect:/voting/" + sessionId;
    }
}
