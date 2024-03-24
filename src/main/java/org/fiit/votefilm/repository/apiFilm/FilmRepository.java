package org.fiit.votefilm.repository.apiFilm;

import org.fiit.votefilm.model.apiFilm.AbstractFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository<T extends AbstractFilm> extends JpaRepository<T, Long> {
    Optional<AbstractFilm> findByTitle(String title);
}
