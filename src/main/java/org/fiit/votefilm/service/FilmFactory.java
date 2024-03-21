package org.fiit.votefilm.service;

import org.fiit.votefilm.dto.MovieResultTMDB;
import org.fiit.votefilm.dto.OMDBResponse;
import org.fiit.votefilm.dto.TMDBResponse;
import org.fiit.votefilm.enums.FilmType;
import org.fiit.votefilm.model.AbstractFilm;
import org.fiit.votefilm.model.Film;
import org.fiit.votefilm.model.OMDBFilm;
import org.fiit.votefilm.model.TMDBFilm;
import org.fiit.votefilm.repository.OMDBFilmRepository;
import org.fiit.votefilm.repository.TMDBFilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

@Component
public class FilmFactory {
    private final FindFilmService findFilmService;
    private final OMDBFilmRepository omdbFilmRepository;
    private final TMDBFilmRepository tmdbFilmRepository;

    @Autowired
    public FilmFactory(FindFilmService findFilmService, OMDBFilmRepository omdbFilmRepository, TMDBFilmRepository tmdbFilmRepository) {
        this.findFilmService = findFilmService;
        this.omdbFilmRepository = omdbFilmRepository;
        this.tmdbFilmRepository = tmdbFilmRepository;
    }

    public Optional<? extends Film> getFilm(String title) {
        Optional<AbstractFilm> omdbFilm = omdbFilmRepository.findByTitle(title);
        if (omdbFilm.isPresent()) {
            return Optional.of((OMDBFilm) omdbFilm.get());
        }
        System.out.println("is present??: " + omdbFilm.isPresent());
        Optional<AbstractFilm> tmdbFilm = tmdbFilmRepository.findByTitle(title);

        if (tmdbFilm.isPresent()) {
            return Optional.of((TMDBFilm) tmdbFilm.get());
        }

        HashMap<FilmType, ResponseEntity<?>> results = findFilmService.findFilm(title);
        if (results.get(FilmType.OMDB) != null) {
            OMDBFilm omdbFilm1 = createFilmFromOMDBResponse((OMDBResponse) results.get(FilmType.OMDB).getBody());
            if (omdbFilmRepository.findByTitle(omdbFilm1.getTitle()).isPresent()) {
                return Optional.of(omdbFilmRepository.findByTitle(omdbFilm1.getTitle()).get());
            }
            omdbFilmRepository.save(omdbFilm1);
            return Optional.of(omdbFilm1);

        } else if (results.get(FilmType.TMDB) != null) {
            TMDBFilm tmdbFilm1 = createFilmFromTMDBResponse((TMDBResponse) results.get(FilmType.TMDB).getBody());
            if (tmdbFilmRepository.findByTitle(tmdbFilm1.getTitle()).isPresent()) {
                return Optional.of(tmdbFilmRepository.findByTitle(tmdbFilm1.getTitle()).get());
            }
            tmdbFilmRepository.save(tmdbFilm1);
            return Optional.of(tmdbFilm1);
        }
        return Optional.empty();
    }

    private OMDBFilm createFilmFromOMDBResponse(OMDBResponse response) {
        OMDBFilm film = new OMDBFilm();
        film.setTitle(response.getTitle());
        film.setLanguage(response.getLanguage());
        film.setDescription(response.getPlot());
        film.setPoster(response.getPoster());
        film.setImdbRating(response.getImdbRating());
        film.setYear(response.getYear());
        film.setGenre(response.getGenre());
        film.setDirector(response.getDirector());
        film.setMetascore(response.getMetascore());
        return film;
    }

    private TMDBFilm createFilmFromTMDBResponse(TMDBResponse response) {
        MovieResultTMDB resultTMDB = response.getResults().stream().max(Comparator.comparingDouble(MovieResultTMDB::getPopularity)).get();
        TMDBFilm film = new TMDBFilm();
        film.setTitle(resultTMDB.getTitle());
        film.setLanguage(resultTMDB.getOriginalLanguage());
        film.setDescription(resultTMDB.getOverview());
        film.setPoster(resultTMDB.getPosterPath());
        film.setPopularity(resultTMDB.getPopularity());
        film.setVoteAverage(resultTMDB.getVoteAverage());
        return film;
    }
}
