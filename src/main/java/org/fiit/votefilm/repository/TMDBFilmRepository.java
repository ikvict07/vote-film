package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.TMDBFilm;
import org.springframework.stereotype.Repository;

@Repository
public interface TMDBFilmRepository extends FilmRepository<TMDBFilm> {
}
