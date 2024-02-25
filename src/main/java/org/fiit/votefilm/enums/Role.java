package org.fiit.votefilm.enums;

public enum Role {
    COMMON_USER("COMMON_USER"),
    VOTING_HOST("VOTING_HOST");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
