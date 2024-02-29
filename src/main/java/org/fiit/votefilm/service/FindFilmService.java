package org.fiit.votefilm.service;

import org.fiit.votefilm.dto.TMDBResponse;
import org.fiit.votefilm.util.FilmResultList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FindFilmService {
    private final FinderOMDB finderOMDB;
    private final FinderTMDB finderTMDB;

    public FindFilmService(FinderOMDB finderOMDB, FinderTMDB finderTMDB) {
        this.finderOMDB = finderOMDB;
        this.finderTMDB = finderTMDB;
    }

    public String findFilm(String title) {
        AtomicBoolean filmFound = new AtomicBoolean(false);
        AtomicReference<ResponseEntity<?>> omdbResponse = new AtomicReference<>();
        AtomicReference<ResponseEntity<?>> tmdbResponse = new AtomicReference<>();

        Runnable omdb = () -> {
            try {
                if (!filmFound.get()) {
                    ResponseEntity<?> response = finderOMDB.sendRequest(title);
                    omdbResponse.set(response);
                    filmFound.set(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Runnable tmdb = () -> {
            try {
                if (!filmFound.get()) {
                    ResponseEntity<?> response = finderTMDB.sendRequest(title);
                    tmdbResponse.set(response);
                    filmFound.set(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread omdbThread = new Thread(omdb);
        Thread tmdbThread = new Thread(tmdb);
        omdbThread.start();
        tmdbThread.start();

        try {
            omdbThread.join();
            tmdbThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseEntity<?> omdbFilmResponse = omdbResponse.get();
        ResponseEntity<?> tmdbFilmResponse = tmdbResponse.get();

        if (omdbFilmResponse != null && omdbFilmResponse.getStatusCode().is2xxSuccessful()) {
            System.out.println(omdbFilmResponse.getBody());
        }
        if (tmdbFilmResponse != null && tmdbFilmResponse.getStatusCode().is2xxSuccessful()) {
            TMDBResponse response = (TMDBResponse) tmdbFilmResponse.getBody();
            FilmResultList films = response.getResults();
            System.out.println(films.getBestFilm());
        }
        return filmFound.get() ? "Film found" : "Film not found";
    }
}
