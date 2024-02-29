package org.fiit.votefilm.controller;

import org.fiit.votefilm.service.FindFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieFinderApi {
    private final FindFilmService finder;

    @Autowired
    public MovieFinderApi(FindFilmService finder) {
        this.finder = finder;
    }

    @GetMapping("/movie")
    public void movie() {
        System.out.println(finder.findFilm("Fast and the Furious"));
    }
}
