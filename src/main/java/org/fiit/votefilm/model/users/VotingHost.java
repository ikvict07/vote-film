package org.fiit.votefilm.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.fiit.votefilm.enums.Role;
import org.fiit.votefilm.model.VotingSession;

import java.util.List;

/**
 * Entity representing a Voting host
 */
@Data
@Entity
public class VotingHost extends VoterUser {
    @OneToMany(mappedBy = "creator")
    private List<VotingSession> votingSession;

    public VotingHost(String username, String password) {
        super(username, password);
        setPoints(10_000_000L);
        setRole(Role.VOTING_HOST);
    }

    public VotingHost() {
        super();
        setRole(Role.VOTING_HOST);
    }
}
