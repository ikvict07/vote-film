package org.fiit.votefilm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.fiit.votefilm.dto.OMDBResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FinderOMDB implements FilmFinder {
    private final ObjectMapper mapper;
    @Value("${api.omdb.key}")
    private String apiKey;

    public FinderOMDB(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<?> findFilm(String title) {
        try {
            return sendRequest(title);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    private String prepareTitle(String title) {
        return title.replaceAll(" ", "+");
    }

    private String prepareUrl(String title) {
        return "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + prepareTitle(title);
    }

    public ResponseEntity<?> sendRequest(String title) throws IOException {
        String url = prepareUrl(title);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            OMDBResponse movieResponse = mapper.readValue(responseBody, OMDBResponse.class);
            if ("False".equals(movieResponse.getResponse())) {
                return ResponseEntity.status(404).body("Film not found");
            }
            return ResponseEntity.ok(movieResponse);
        }
    }
}
