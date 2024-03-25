package org.fiit.votefilm.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import org.fiit.votefilm.enums.Role;

/**
 * Entity representing a voter user.
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class VoterUser extends AbstractUser {

    private Long points;

    public VoterUser() {
    }
    public VoterUser(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(Role.COMMON_USER);
        this.points = 100L;
    }
}
