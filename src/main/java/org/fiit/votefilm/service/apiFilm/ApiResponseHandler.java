package org.fiit.votefilm.service.apiFilm;

import org.springframework.http.ResponseEntity;

public abstract class ApiResponseHandler<T> {
    abstract public ResponseEntity<?> handleResponse(T response);
}
