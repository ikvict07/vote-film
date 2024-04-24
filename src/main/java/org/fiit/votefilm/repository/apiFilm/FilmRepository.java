package org.fiit.votefilm.repository.apiFilm;

import org.fiit.votefilm.model.apiFilm.AbstractFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for films.
 *
 * @param <T> The type of the film.
 */
@Repository
public interface FilmRepository<T extends AbstractFilm> extends JpaRepository<T, Long> {
    /**
     * Find a film by its title.
     *
     * @param title The title of the film.
     * @return The film with the given title.
     */
    Optional<AbstractFilm> findByTitle(String title);
}
