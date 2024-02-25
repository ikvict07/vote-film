package org.fiit.votefilm.exceptions;


/**
 * Exception thrown when a user provides a wrong password.
 */
public class WrongPasswordException extends AuthenticationFailedException {
    public WrongPasswordException(String e) {
        super(e);
    }
}
