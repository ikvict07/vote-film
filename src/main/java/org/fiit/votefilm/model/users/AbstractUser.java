package org.fiit.votefilm.model.users;

import jakarta.persistence.*;
import lombok.Data;
import org.fiit.votefilm.enums.Role;

@Entity
@Data
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
