package org.fiit.votefilm.exceptions;

/**
 * Exception thrown when an Authentication fails.
 */
public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
