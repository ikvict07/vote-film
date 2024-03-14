package org.fiit.votefilm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public abstract class AbstractFilm implements Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "poster", nullable = true)
    private String poster;

    @OneToMany(mappedBy = "film")
    private List<VotingItem> votingItems;
}
