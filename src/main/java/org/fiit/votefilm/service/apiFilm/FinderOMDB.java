package org.fiit.votefilm.service.apiFilm;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.fiit.votefilm.dto.OMDBResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Finder for films in the OMDB API.
 */
@Component
public class FinderOMDB implements FilmFinder {
    private final ObjectMapper mapper;
    @Value("${api.omdb.key}")
    private String apiKey;

    private final OMDBResponseHandler omdbResponseHandler;

    /**
     * Constructor for the FinderOMDB.
     *
     * @param mapper The object mapper.
     */
    public FinderOMDB(ObjectMapper mapper) {
        this.mapper = mapper;
        omdbResponseHandler = new OMDBResponseHandler();
    }


    private String prepareTitle(String title) {
        return title.replaceAll(" ", "+");
    }

    private String prepareUrl(String title) {
        return "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + prepareTitle(title);
    }

    /**
     * Find a film by its title on the OMDB API.
     *
     * @param title The title of the film.
     * @return The response entity with the film.
     */
    @Override
    public ResponseEntity<?> findFilm(String title) throws IOException {
        String url = prepareUrl(title);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            OMDBResponse movieResponse = mapper.readValue(responseBody, OMDBResponse.class);
            return omdbResponseHandler.handleResponse(movieResponse);
        }
    }
}
