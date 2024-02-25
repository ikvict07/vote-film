package org.fiit.votefilm.exceptions;

public class UserAlreadyRegisteredException extends AuthenticationFailedException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
