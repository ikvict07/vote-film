package org.fiit.votefilm.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.fiit.votefilm.enums.Role;
import org.fiit.votefilm.model.VotingSession;

import java.util.List;
import java.util.Objects;

/**
 * Entity representing a Voting host
 */
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

    public List<VotingSession> getVotingSession() {
        return this.votingSession;
    }

    public void setVotingSession(List<VotingSession> votingSession) {
        this.votingSession = votingSession;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof VotingHost other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$votingSession = this.getVotingSession();
        final Object other$votingSession = other.getVotingSession();
        return Objects.equals(this$votingSession, other$votingSession);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof VotingHost;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $votingSession = this.getVotingSession();
        result = result * PRIME + ($votingSession == null ? 43 : $votingSession.hashCode());
        return result;
    }

    public String toString() {
        return "VotingHost(votingSession=" + this.getVotingSession() + ")";
    }
}
