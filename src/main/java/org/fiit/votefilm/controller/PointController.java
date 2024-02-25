package org.fiit.votefilm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.fiit.votefilm.exceptions.AccessNotAllowed;
import org.fiit.votefilm.exceptions.AuthenticationFailedException;
import org.fiit.votefilm.service.PointsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for the points page.
 * This controller created for adding twitch connectivity to the application in the future.
 */
@Controller
@RequestMapping("/points")
public class PointController {
    private final PointsService pointsService;

    public PointController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    /**
     * Handles POST requests to add points to a user.
     *
     * @param username the username of the user
     * @param points   the number of points to add
     * @param request  the HttpServletRequest object
     * @return a redirect URL depending on the outcome of the operation
     */
    @PostMapping("/add/")
    public String addPoints(@RequestParam String username, @RequestParam int points, HttpServletRequest request) {
        try {
            pointsService.addPoints(username, points);
        } catch (AccessNotAllowed e) {
            return "redirect:/";
        } catch (AuthenticationFailedException e) {
            return "redirect:/auth/login/";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    /**
     * Handles POST requests to remove points from a user.
     *
     * @param username the username of the user
     * @param points   the number of points to remove
     * @param request  the HttpServletRequest object
     * @return a redirect URL depending on the outcome of the operation
     */
    @PostMapping("/remove/")
    public String removePoints(@RequestParam String username, @RequestParam int points, HttpServletRequest request) {
        try {
            pointsService.removePoints(username, points);
        } catch (AccessNotAllowed e) {
            return "redirect:/";
        } catch (AuthenticationFailedException e) {
            return "redirect:/auth/login/";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
