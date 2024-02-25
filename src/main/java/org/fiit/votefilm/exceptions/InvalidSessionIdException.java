package org.fiit.votefilm.exceptions;

/**
 * Exception thrown when an invalid session ID is provided.
 */
public class InvalidSessionIdException extends Throwable {
    public InvalidSessionIdException(String e) {
        super(e);
    }
}
