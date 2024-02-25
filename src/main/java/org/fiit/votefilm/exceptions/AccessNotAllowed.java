package org.fiit.votefilm.exceptions;

/**
 * Exception thrown when a user tries to access a resource they are not allowed to.
 */
public class AccessNotAllowed extends Exception {
    public AccessNotAllowed(String message) {
        super(message);
    }
}
