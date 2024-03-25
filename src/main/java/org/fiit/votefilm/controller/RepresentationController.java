package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.fiit.votefilm.exceptions.InvalidSessionIdException;
import org.fiit.votefilm.model.VotingItem;
import org.fiit.votefilm.model.apiFilm.Film;
import org.fiit.votefilm.model.apiFilm.OMDBFilm;
import org.fiit.votefilm.model.apiFilm.TMDBFilm;
import org.fiit.votefilm.service.VotingLogic;
import org.fiit.votefilm.service.apiFilm.FilmFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for the representation page.
 */
@Controller
public class RepresentationController {

    private final VotingLogic votingLogic;
    private final FilmFactory filmFactory;


    public RepresentationController(VotingLogic votingLogic, FilmFactory filmFactory) {

        this.votingLogic = votingLogic;

        this.filmFactory = filmFactory;
    }

    /**
     * Handles GET requests to the index page.
     *
     * @return redirect to the voting page
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/voting/enter/";
    }

    /**
     * Handles GET requests to the voting page for a specific session.
     *
     * @param model              the Model object
     * @param id                 the unique code of the voting session
     * @param redirectAttributes the RedirectAttributes object
     * @return the voting view
     */
    @GetMapping("/voting/{id}")
    public String votingList(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
        if (addVotingItemsToModel(model, id, redirectAttributes)) return "redirect:/voting/enter/";
        return "voting";
    }

    /**
     * Handles GET requests to the voting enter page.
     *
     * @return the voting enter view
     */
    @GetMapping("/voting/enter/")
    public String votingEnter() {
        return "voting-enter";
    }

    /**
     * Handles POST requests to the voting enter page.
     *
     * @param sessionId the unique code of the voting session
     * @return a redirect URL depending on the outcome of the operation
     */
    @PostMapping("/voting/enter/")
    public String votingEnterSubmit(@RequestParam String sessionId) {
        return "redirect:/voting/" + sessionId;
    }

    /**
     * Handles GET requests to the voting spin page.
     *
     * @param model              the Model object
     * @param id                 the unique code of the voting session
     * @param redirectAttributes the RedirectAttributes object
     * @return the roulette view
     */
    @GetMapping("/voting/spin/{id}")
    public String votingSpin(Model model, @PathVariable String id, RedirectAttributes redirectAttributes) {
        if (addVotingItemsToModel(model, id, redirectAttributes)) return "redirect:/voting/enter/";
        return "roulette";
    }

    /**
     * Adds voting items to the model for the voting page.
     *
     * @param model              the Model object
     * @param id                 the unique code of the voting session
     * @param redirectAttributes the RedirectAttributes object
     * @return true if an InvalidSessionIdException is caught, false otherwise
     */
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

    @GetMapping("/voting/film")
    private String filmInfo(Model model, @RequestParam String title, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        System.out.println("TITLE IS: " + title);
        Film film = filmFactory.getFilm(title).orElse(null);

        if (film == null) {
            redirectAttributes.addFlashAttribute("error", "Film not found in the database");
            return "redirect:" + request.getHeader("Referer");
        }
        if (film instanceof OMDBFilm omdbFilm) {
            model.addAttribute("film", omdbFilm);
            return "film-info-omdb";
        } else if (film instanceof TMDBFilm tmdbFilm) {
            model.addAttribute("film", tmdbFilm);
            return "film-info-tmdb";
        }
        redirectAttributes.addFlashAttribute("error", "Film not found in the database");
        return "redirect:" + request.getHeader("Referer");
    }
}
