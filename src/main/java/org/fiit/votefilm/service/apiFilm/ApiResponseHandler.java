package org.fiit.votefilm.service.apiFilm;

import org.springframework.http.ResponseEntity;

/**
 * Interface for handling API responses.
 *
 * @param <T> The type of the response.
 */
public abstract class ApiResponseHandler<T> {
    /**
     * Handle the response.
     *
     * @param response The response to handle.
     * @return The response entity.
     */
    abstract public ResponseEntity<?> handleResponse(T response);
}
