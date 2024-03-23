package org.fiit.votefilm.service.filmApi;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface FilmFinder {
    ResponseEntity<?> findFilm(String title) throws IOException;
}
