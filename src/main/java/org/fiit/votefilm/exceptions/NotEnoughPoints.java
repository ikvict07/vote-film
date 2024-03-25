package org.fiit.votefilm.exceptions;

/**
 * Exception thrown when a user tries to vote with not enough points.
 */
public class NotEnoughPoints extends Throwable {
    public NotEnoughPoints(String e) {
        super(e);
    }
}
