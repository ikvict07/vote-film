package org.fiit.votefilm.model.users;

import jakarta.persistence.Entity;
import lombok.Data;
import org.fiit.votefilm.enums.Role;


@Entity
@Data
public class Admin extends AbstractUser {
    public Admin(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
        setRole(Role.ADMIN);
    }

    public Admin() {
        super();
        setRole(Role.ADMIN);
    }
}
