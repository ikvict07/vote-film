package org.fiit.votefilm.exceptions;

public class WrongPasswordException extends AuthenticationFailedException {
    public WrongPasswordException(String e) {
        super(e);
    }
}
