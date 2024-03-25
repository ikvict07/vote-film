package org.fiit.votefilm.service.apiFilm;

import org.fiit.votefilm.dto.OMDBResponse;
import org.springframework.http.ResponseEntity;


public class OMDBResponseHandler extends ApiResponseHandler<OMDBResponse> {

    @Override
    public ResponseEntity<?> handleResponse(OMDBResponse response) {
        if ("False".equals(response.getResponse())) {
            return ResponseEntity.status(404).body("Film not found");
        }
        return ResponseEntity.ok(response);
    }
}