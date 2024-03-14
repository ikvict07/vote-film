package org.fiit.votefilm.repository;

import org.fiit.votefilm.model.AbstractFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository<T extends AbstractFilm> extends JpaRepository<T, Long> {
    Optional<AbstractFilm> findByTitle(String title);
}
