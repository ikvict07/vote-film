package org.fiit.votefilm.service.apiFilm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.fiit.votefilm.dto.TMDBResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FinderTMDB implements FilmFinder {
    private final ObjectMapper objectMapper;
    @Value("${api.tmdb.key}")
    private String apiKey;


    private final TMDBResponseHandler tmdbResponseHandler;
    public FinderTMDB(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.tmdbResponseHandler = new TMDBResponseHandler();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("API Key: " + apiKey);
    }


    private String prepareTitle(String title) {
        return title.replaceAll(" ", "+");
    }

    private String prepareUrl(String title) {
        return "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + prepareTitle(title)
                + "&language=en-US"
                + "&page=1"
                + "&include_adult=true"
                + "&region=US";
    }

    @Override
    public ResponseEntity<?> findFilm(String title) throws IOException {
        String url = prepareUrl(title);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer %s".formatted(apiKey))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            TMDBResponse tmdbResponse = objectMapper.readValue(responseBody, TMDBResponse.class);
            return tmdbResponseHandler.handleResponse(tmdbResponse);
        }
    }
}
