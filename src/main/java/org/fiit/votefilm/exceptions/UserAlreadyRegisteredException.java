package org.fiit.votefilm.exceptions;


/**
 * Exception thrown when a user tries to register with an already registered username.
 */
public class UserAlreadyRegisteredException extends AuthenticationFailedException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
