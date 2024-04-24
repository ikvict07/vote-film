package org.fiit.votefilm.repository.apiFilm;

import org.fiit.votefilm.model.apiFilm.OMDBFilm;
import org.springframework.stereotype.Repository;

/**
 * Repository for films from the OMDB API.
 */
@Repository
public interface OMDBFilmRepository extends FilmRepository<OMDBFilm> {

}
