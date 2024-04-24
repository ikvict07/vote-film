package org.fiit.votefilm.service.apiFilm;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * Interface for finding films.
 */
public interface FilmFinder {
    /**
     * Find a film by its title.
     *
     * @param title The title of the film.
     * @return The response entity with the film.
     */
    ResponseEntity<?> findFilm(String title) throws IOException;
}
