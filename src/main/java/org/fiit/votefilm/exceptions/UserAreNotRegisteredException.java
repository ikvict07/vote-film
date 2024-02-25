package org.fiit.votefilm.exceptions;

public class UserAreNotRegisteredException extends AuthenticationFailedException {
    public UserAreNotRegisteredException(String e) {
        super(e);
    }
}
