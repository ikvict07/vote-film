package org.fiit.votefilm.model.users;

import jakarta.persistence.Entity;
import org.fiit.votefilm.enums.Role;


@Entity
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Admin other)) return false;
        return other.canEqual(this);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Admin;
    }

    public int hashCode() {
        int result = 1;
        return result;
    }

    public String toString() {
        return "Admin()";
    }
}
