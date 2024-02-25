package org.fiit.votefilm.controller;

import org.fiit.votefilm.service.PointsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/points")
public class PointController {
    private final PointsService pointsService;

    public PointController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @PostMapping("/add/")
    public void addPoints(@RequestParam String username, @RequestParam int points) {
        pointsService.addPoints(username, points);
    }

    @PostMapping("/remove/")
    public void removePoints(@RequestParam String username, @RequestParam int points) {
        pointsService.removePoints(username, points);
    }
}
