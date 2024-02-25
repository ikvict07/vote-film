package org.fiit.votefilm.controller;

import org.fiit.votefilm.exceptions.InvalidSessionIdException;
import org.fiit.votefilm.model.VotingItem;
import org.fiit.votefilm.repository.VotingItemRepository;
import org.fiit.votefilm.service.VotingLogic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RepresentationController {
    private final VotingItemRepository votingItemRepository;
    private final VotingLogic votingLogic;

    public RepresentationController(VotingItemRepository votingItemRepository, VotingLogic votingLogic) {
        this.votingItemRepository = votingItemRepository;
        this.votingLogic = votingLogic;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/voting/enter/";
    }

    @GetMapping("/voting/")
    public String votingList(Model model) {
        model.addAttribute("votingItems", votingItemRepository.findAll());
        model.addAttribute("votes", votingItemRepository.findAll().stream().map(VotingItem::getVotes));
        return "voting";
    }

    @GetMapping("/voting/{id}")
    public String votingList(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
        if (addVotingItemsToModel(model, id, redirectAttributes)) return "redirect:/voting/enter/";
        return "voting";
    }

    @GetMapping("/voting/enter/")
    public String votingEnter() {
        return "voting-enter";
    }

    @PostMapping("/voting/enter/")
    public String votingEnterSubmit(@RequestParam String sessionId) {
        return "redirect:/voting/" + sessionId;
    }

    @GetMapping("/voting/spin/{id}")
    public String votingSpin(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
        if (addVotingItemsToModel(model, id, redirectAttributes)) return "redirect:/voting/enter/";
        return "roulette";
    }

    private boolean addVotingItemsToModel(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
        List<VotingItem> votingItems;
        try {
            votingItems = votingLogic.getVotingItems(id);
            model.addAttribute("votingItems", votingLogic.getVotingItems(id));
            model.addAttribute("votes", votingLogic.getVotingItems(id).stream().map(VotingItem::getVotes));
            model.addAttribute("sessionId", id);
            model.addAttribute("title", votingLogic.getVotingSession(id).getTitle());
        } catch (InvalidSessionIdException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return true;
        }
        Long totalVotes = votingItems.stream().mapToLong(VotingItem::getVotes).sum();
        model.addAttribute("totalVotes", totalVotes);
        return false;
    }
}
