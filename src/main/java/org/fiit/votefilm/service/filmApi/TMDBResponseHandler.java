package org.fiit.votefilm.service.filmApi;

import org.fiit.votefilm.dto.TMDBResponse;
import org.springframework.http.ResponseEntity;

public class TMDBResponseHandler extends ApiResponseHandler<TMDBResponse> {
    @Override
    public ResponseEntity<?> handleResponse(TMDBResponse response) {
        if (response.getTotalResults() == 0) {
            return ResponseEntity.status(404).body("Film not found");
        }
        return ResponseEntity.ok(response);
    }
}