package org.fiit.votefilm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VoterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;
    private Long points;

    public VoterUser() {
    }
    public VoterUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
