package org.fiit.votefilm.model.apiFilm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class for a film from the OMDB API.
 */
@Entity
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

    public OMDBFilm() {
    }

    /**
     * Get the URL of the poster.
     *
     * @return The URL of the poster.
     */
    @Override
    public String getPoster() {
        return super.getPoster();
    }

    /**
     * Get the year of the film.
     *
     * @return The year of the film.
     */
    public String getYear() {
        return this.year;
    }

    /**
     * Get the genre of the film.
     *
     * @return The genre of the film.
     */
    public String getGenre() {
        return this.genre;
    }

    /**
     * Get the director of the film.
     *
     * @return The director of the film.
     */
    public String getDirector() {
        return this.director;
    }

    /**
     * Get the metascore of the film.
     *
     * @return The metascore of the film.
     */
    public String getMetascore() {
        return this.metascore;
    }

    /**
     * Get the IMDB rating of the film.
     *
     * @return The IMDB rating of the film.
     */
    public String getImdbRating() {
        return this.imdbRating;
    }


    public void setYear(String year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OMDBFilm other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$year = this.getYear();
        final Object other$year = other.getYear();
        if (!Objects.equals(this$year, other$year)) return false;
        final Object this$genre = this.getGenre();
        final Object other$genre = other.getGenre();
        if (!Objects.equals(this$genre, other$genre)) return false;
        final Object this$director = this.getDirector();
        final Object other$director = other.getDirector();
        if (!Objects.equals(this$director, other$director)) return false;
        final Object this$metascore = this.getMetascore();
        final Object other$metascore = other.getMetascore();
        if (!Objects.equals(this$metascore, other$metascore)) return false;
        final Object this$imdbRating = this.getImdbRating();
        final Object other$imdbRating = other.getImdbRating();
        return Objects.equals(this$imdbRating, other$imdbRating);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OMDBFilm;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $year = this.getYear();
        result = result * PRIME + ($year == null ? 43 : $year.hashCode());
        final Object $genre = this.getGenre();
        result = result * PRIME + ($genre == null ? 43 : $genre.hashCode());
        final Object $director = this.getDirector();
        result = result * PRIME + ($director == null ? 43 : $director.hashCode());
        final Object $metascore = this.getMetascore();
        result = result * PRIME + ($metascore == null ? 43 : $metascore.hashCode());
        final Object $imdbRating = this.getImdbRating();
        result = result * PRIME + ($imdbRating == null ? 43 : $imdbRating.hashCode());
        return result;
    }

    public String toString() {
        return "OMDBFilm(year=" + this.getYear() + ", genre=" + this.getGenre() + ", director=" + this.getDirector() + ", metascore=" + this.getMetascore() + ", imdbRating=" + this.getImdbRating() + ")";
    }
}
