package org.fiit.votefilm.model;

import jakarta.persistence.*;
import lombok.Data;
import org.fiit.votefilm.enums.Role;

import java.util.List;

@Data
@Entity
public class SuperUser extends VoterUser {
    @OneToMany(mappedBy = "creator")
    private List<VotingSession> votingSession;

    public SuperUser(String username, String password) {
        super(username, password);
        setPoints(10_000_000L);
        setRole(Role.VOTING_HOST);
    }

    public SuperUser() {
        super();
        setRole(Role.VOTING_HOST);
    }
}
