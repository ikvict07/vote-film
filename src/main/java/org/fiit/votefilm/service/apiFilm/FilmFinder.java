package org.fiit.votefilm.service.apiFilm;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface FilmFinder {
    ResponseEntity<?> findFilm(String title) throws IOException;
}
