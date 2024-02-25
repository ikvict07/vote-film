package org.fiit.votefilm.exceptions;

/**
 * Exception thrown when a user is not registered.
 */
public class UserIsNotRegisteredException extends AuthenticationFailedException {
    public UserIsNotRegisteredException(String e) {
        super(e);
    }
}
