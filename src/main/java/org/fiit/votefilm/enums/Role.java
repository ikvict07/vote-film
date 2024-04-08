package org.fiit.votefilm.enums;

import lombok.Getter;

/**
 * Enum representing the roles of the users.
 */
@Getter
public enum Role {
    COMMON_USER("COMMON_USER"),
    VOTING_HOST("VOTING_HOST"),
    ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
