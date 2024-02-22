package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpSession;
import org.fiit.votefilm.model.VotingItem;
import org.fiit.votefilm.repository.VotingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RepresentationController {
    private final VotingItemRepository votingItemRepository;
    public RepresentationController(VotingItemRepository votingItemRepository) {
        this.votingItemRepository = votingItemRepository;
    }

    @GetMapping("/voting")
    public String votingList(Model model, HttpSession session) {

        System.out.println("user is:" + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(System.out::println);
        System.out.println("session is:" + session.getAttribute("username"));
        model.addAttribute("votingItems", votingItemRepository.findAll());
        model.addAttribute("votes", votingItemRepository.findAll().stream().map(VotingItem::getVotes));
        return "voting";
    }
}
