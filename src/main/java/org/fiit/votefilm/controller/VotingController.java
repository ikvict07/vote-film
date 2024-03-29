package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.exceptions.InvalidSessionIdException;
import org.fiit.votefilm.exceptions.NotEnoughPoints;
import org.fiit.votefilm.service.VotingLogic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for the voting page.
 */
@Controller
public class VotingController {

    private final VotingLogic votingLogic;

    public VotingController(VotingLogic votingLogic) {
        this.votingLogic = votingLogic;
    }

    /**
     * Handles POST requests to vote for a movie.
     *
     * @param title              the title of the movie
     * @param sessionId          the unique code of the voting session
     * @param votes              the number of votes to add
     * @param request            the HttpServletRequest object
     * @param redirectAttributes the RedirectAttributes object
     * @return a redirect URL depending on the outcome of the operation
     */

    @PostMapping("/vote/")
    public String voteSubmit(@RequestParam String title, @RequestParam String sessionId, @RequestParam Long votes, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String referer = request.getHeader("Referer");

        try {
            votingLogic.vote(title, sessionId, votes);
        } catch (AuthenticationFailedException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/login/";
        } catch (NotEnoughPoints e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:" + referer;
        } catch (InvalidSessionIdException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/voting/enter/";
        }
        return "redirect:" + referer;
    }
}
