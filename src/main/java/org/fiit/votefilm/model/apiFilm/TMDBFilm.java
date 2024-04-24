package org.fiit.votefilm.model.apiFilm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.io.Serializable;

/**
 * Entity class for a film from the TMDB API.
 */
@Entity
public class TMDBFilm extends AbstractFilm implements Serializable {
    public TMDBFilm() {
    }

    /**
     * Get the URL of the poster.
     *
     * @return The URL of the poster.
     */
    @Override
    public String getPoster() {
        return "https://image.tmdb.org/t/p/original" + super.getPoster();
    }

    @Column(name = "vote_average", nullable = true)
    private double voteAverage;

    @Column(name = "popularity", nullable = true)
    private double popularity;

    public double getVoteAverage() {
        return this.voteAverage;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TMDBFilm other)) return false;
        if (!other.canEqual(this)) return false;
        if (Double.compare(this.getVoteAverage(), other.getVoteAverage()) != 0) return false;
        return Double.compare(this.getPopularity(), other.getPopularity()) == 0;
    }

    /**
     * Check if the object can be equal to this.
     *
     * @param other The object to compare.
     * @return True if the object can be equal to this, false otherwise.
     */
    protected boolean canEqual(final Object other) {
        return other instanceof TMDBFilm;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $voteAverage = Double.doubleToLongBits(this.getVoteAverage());
        result = result * PRIME + (int) ($voteAverage >>> 32 ^ $voteAverage);
        final long $popularity = Double.doubleToLongBits(this.getPopularity());
        result = result * PRIME + (int) ($popularity >>> 32 ^ $popularity);
        return result;
    }

    public String toString() {
        return "TMDBFilm(voteAverage=" + this.getVoteAverage() + ", popularity=" + this.getPopularity() + ")";
    }
}
