package org.fiit.votefilm.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import org.fiit.votefilm.enums.Role;

import java.util.Objects;

/**
 * Entity representing a voter user.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class VoterUser extends AbstractUser {

    private Long points;

    public VoterUser() {
    }


    /**
     * Create a new voter user with 100 points.
     *
     * @param username The username of the voter.
     * @param password The password of the voter.
     */
    public VoterUser(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(Role.COMMON_USER);
        this.points = 100L;
    }

    public Long getPoints() {
        return this.points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof VoterUser other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$points = this.getPoints();
        final Object other$points = other.getPoints();
        return Objects.equals(this$points, other$points);
    }

    /**
     * Check if the object can be equal to this.
     *
     * @param other The object to compare.
     * @return True if the object can be equal to this, false otherwise.
     */
    protected boolean canEqual(final Object other) {
        return other instanceof VoterUser;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $points = this.getPoints();
        result = result * PRIME + ($points == null ? 43 : $points.hashCode());
        return result;
    }

    public String toString() {
        return "VoterUser(points=" + this.getPoints() + ")";
    }
}
