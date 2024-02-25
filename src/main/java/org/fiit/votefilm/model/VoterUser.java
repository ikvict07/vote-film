package org.fiit.votefilm.model;

import jakarta.persistence.*;
import lombok.Data;
import org.fiit.votefilm.enums.Role;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class VoterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;
    private Long points;


    @Enumerated(EnumType.STRING)
    private Role role;

    public VoterUser() {
    }
    public VoterUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.COMMON_USER;
        this.points = 100L;
    }
}
