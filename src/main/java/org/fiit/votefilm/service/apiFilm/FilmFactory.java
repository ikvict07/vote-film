package org.fiit.votefilm.service.apiFilm;

import org.fiit.votefilm.dto.MovieResultTMDB;
import org.fiit.votefilm.dto.OMDBResponse;
import org.fiit.votefilm.dto.TMDBResponse;
import org.fiit.votefilm.enums.FilmType;
import org.fiit.votefilm.model.apiFilm.AbstractFilm;
import org.fiit.votefilm.model.apiFilm.OMDBFilm;
import org.fiit.votefilm.model.apiFilm.TMDBFilm;
import org.fiit.votefilm.repository.apiFilm.OMDBFilmRepository;
import org.fiit.votefilm.repository.apiFilm.TMDBFilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

/**
 * Factory for creating films.
 */
@Component
public class FilmFactory {
    private final FindFilmService findFilmService;
    private final OMDBFilmRepository omdbFilmRepository;
    private final TMDBFilmRepository tmdbFilmRepository;

    /**
     * Constructor for the FilmFactory.
     *
     * @param findFilmService    The service for finding films.
     * @param omdbFilmRepository The repository for films from the OMDB API.
     * @param tmdbFilmRepository The repository for films from the TMDB API.
     */
    @Autowired
    public FilmFactory(FindFilmService findFilmService, OMDBFilmRepository omdbFilmRepository, TMDBFilmRepository tmdbFilmRepository) {
        this.findFilmService = findFilmService;
        this.omdbFilmRepository = omdbFilmRepository;
        this.tmdbFilmRepository = tmdbFilmRepository;
    }

    /**
     * Get a film with the given title.
     *
     * @param title The title of the film.
     * @return The optional film with the given title. Tries to find the film in the cache first, then in the OMDB API and finally in the TMDB API.
     */
    public Optional<? extends AbstractFilm> getFilm(String title) {
        Optional<AbstractFilm> cachedFilm = loadFilmFromCache(title);
        if (cachedFilm.isPresent()) {
            return cachedFilm;
        }

        HashMap<FilmType, ResponseEntity<?>> results = findFilmService.findFilm(title);
        if (results.get(FilmType.OMDB) != null) {
            OMDBFilm omdbFilm = createFilmFromOMDBResponse((OMDBResponse) results.get(FilmType.OMDB).getBody());
            omdbFilmRepository.save(omdbFilm);
            saveFilmToCache(omdbFilm);
            return Optional.of(omdbFilm);
        } else if (results.get(FilmType.TMDB) != null) {
            TMDBFilm tmdbFilm = createFilmFromTMDBResponse((TMDBResponse) results.get(FilmType.TMDB).getBody());
            tmdbFilmRepository.save(tmdbFilm);
            saveFilmToCache(tmdbFilm);
            return Optional.of(tmdbFilm);
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

    /**
     * Save a film to the cache.
     *
     * @param film The film to save.
     */
    public void saveFilmToCache(AbstractFilm film) {
        try {
            File directory = new File("src/main/resources/cache/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileOutputStream fileOut = new FileOutputStream(directory.getPath() + "/" + film.getTitle() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(film);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a film from the cache.
     *
     * @param title The title of the film.
     * @return The optional film with the given title.
     */
    public Optional<AbstractFilm> loadFilmFromCache(String title) {
        AbstractFilm film = null;

        try {
            FileInputStream fileIn = new FileInputStream("src/main/resources/cache/" + title + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            film = (AbstractFilm) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println(ignored.getMessage());
            return Optional.empty();
        }

        return Optional.of(film);
    }
}
