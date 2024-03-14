package org.fiit.votefilm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class TMDBFilm extends AbstractFilm {
    @Override
    public String getPoster() {
        return "https://image.tmdb.org/t/p/original" + super.getPoster();
    }

    @Column(name = "vote_average", nullable = true)
    private double voteAverage;

    @Column(name = "popularity", nullable = true)
    private double popularity;

}
