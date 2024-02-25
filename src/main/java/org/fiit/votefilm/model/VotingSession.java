package org.fiit.votefilm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Entity representing a voting session.
 */
@Data
@Entity
public class VotingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "unique_code", nullable = false, unique = true)
    private String uniqueCode;

    public VotingSession() {

    }

    @PrePersist
    protected void generateUniqueCode() {
        uniqueCode = UUID.randomUUID().toString();
    }

    @OneToMany(mappedBy = "votingSession")
    @Column(name = "voting_items")
    private List<VotingItem> votingItems;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private SuperUser creator;


    @Column(name = "is_open", nullable = false)
    private boolean isOpen;


    public VotingSession(SuperUser creator, String title) {
        this.creator = creator;
        this.title = title;
        this.isOpen = true;
    }

}
