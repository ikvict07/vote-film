package org.fiit.votefilm.service.apiFilm;

import org.fiit.votefilm.dto.TMDBResponse;
import org.springframework.http.ResponseEntity;

/**
 * Handler for TMDB API responses.
 */
public class TMDBResponseHandler extends ApiResponseHandler<TMDBResponse> {
    /**
     * Handle the response.
     *
     * @param response The response to handle.
     * @return The response entity.
     */
    @Override
    public ResponseEntity<?> handleResponse(TMDBResponse response) {
        if (response.getTotalResults() == 0) {
            return ResponseEntity.status(404).body("Film not found");
        }
        return ResponseEntity.ok(response);
    }
}