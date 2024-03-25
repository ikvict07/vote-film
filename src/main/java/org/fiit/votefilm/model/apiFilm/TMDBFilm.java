package org.fiit.votefilm.model.apiFilm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class TMDBFilm extends AbstractFilm implements Serializable {
    @Override
    public String getPoster() {
        return "https://image.tmdb.org/t/p/original" + super.getPoster();
    }

    @Column(name = "vote_average", nullable = true)
    private double voteAverage;

    @Column(name = "popularity", nullable = true)
    private double popularity;

}
