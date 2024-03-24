package org.fiit.votefilm.model.apiFilm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class OMDBFilm extends AbstractFilm implements Serializable {
    @Column(name = "year", nullable = true)
    private String year;

    @Column(name = "genre", nullable = true)
    private String genre;

    @Column(name = "director", nullable = true)
    private String director;

    @Column(name = "metascore", nullable = true)
    private String metascore;

    @Column(name = "imdb_rating", nullable = true)
    private String imdbRating;

    @Override
    public String getPoster() {
        return super.getPoster();
    }
}
