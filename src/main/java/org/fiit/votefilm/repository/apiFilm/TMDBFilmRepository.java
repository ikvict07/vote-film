package org.fiit.votefilm.repository.apiFilm;

import org.fiit.votefilm.model.apiFilm.TMDBFilm;
import org.springframework.stereotype.Repository;

/**
 * Repository for films from the TMDB API.
 */
@Repository
public interface TMDBFilmRepository extends FilmRepository<TMDBFilm> {
}
