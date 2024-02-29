package org.fiit.votefilm.service;

import org.springframework.http.ResponseEntity;

public interface FilmFinder {
    ResponseEntity<?> findFilm(String title);
}
