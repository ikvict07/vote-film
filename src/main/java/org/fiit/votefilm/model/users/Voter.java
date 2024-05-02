package org.fiit.votefilm.model.users;

import org.fiit.votefilm.exceptions.NotEnoughPoints;

public interface Voter {
    void vote(int pointNum) throws NotEnoughPoints;
}
