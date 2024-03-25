package org.fiit.votefilm.service.apiFilm;

import org.fiit.votefilm.dto.OMDBResponse;
import org.fiit.votefilm.dto.TMDBResponse;
import org.fiit.votefilm.enums.FilmType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public HashMap<FilmType, ResponseEntity<?>> findFilm(String title) {
        AtomicBoolean filmFound = new AtomicBoolean(false);
        AtomicReference<ResponseEntity<?>> omdbResponse = new AtomicReference<>();
        AtomicReference<ResponseEntity<?>> tmdbResponse = new AtomicReference<>();

        Runnable omdb = () -> {
            try {
                if (!filmFound.get()) {
                    ResponseEntity<?> response = finderOMDB.findFilm(title);
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
                    ResponseEntity<?> response = finderTMDB.findFilm(title);
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
        HashMap<FilmType, ResponseEntity<?>> response = new HashMap<>();

        if (!filmFound.get()) {
            return response;
        }
        if (omdbFilmResponse != null && omdbFilmResponse.getStatusCode().is2xxSuccessful()) {
            if (((OMDBResponse) omdbFilmResponse.getBody()).getResponse().equals("True")) {
                response.put(FilmType.OMDB, omdbFilmResponse);
            }
        }
        if (tmdbFilmResponse != null && tmdbFilmResponse.getStatusCode().is2xxSuccessful()) {
            if (((TMDBResponse) tmdbFilmResponse.getBody()).getTotalResults() > 0) {
                response.put(FilmType.TMDB, tmdbFilmResponse);
            }
        }
        return response;
    }

}
