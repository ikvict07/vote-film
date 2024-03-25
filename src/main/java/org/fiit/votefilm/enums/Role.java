package org.fiit.votefilm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the roles of the users.
 */
@Getter
@AllArgsConstructor
public enum Role {
    COMMON_USER("COMMON_USER"),
    VOTING_HOST("VOTING_HOST"),
    ADMIN("ADMIN");

    private final String role;

}
