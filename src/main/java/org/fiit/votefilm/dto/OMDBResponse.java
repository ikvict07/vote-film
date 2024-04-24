package org.fiit.votefilm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * Represents a movie result from The OMDB Movie Database API.
 */
public class OMDBResponse {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Rated")
    private String rated;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Writer")
    private String writer;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Awards")
    private String awards;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("Ratings")
    private List<Rating> ratings;

    @JsonProperty("Metascore")
    private String metascore;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("DVD")
    private String dvd;

    @JsonProperty("BoxOffice")
    private String boxOffice;

    @JsonProperty("Production")
    private String production;

    @JsonProperty("Website")
    private String website;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Error")
    private String error;

    public OMDBResponse() {
    }

    public String getTitle() {
        return this.title;
    }

    public String getYear() {
        return this.year;
    }

    public String getRated() {
        return this.rated;
    }

    public String getReleased() {
        return this.released;
    }

    public String getRuntime() {
        return this.runtime;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getDirector() {
        return this.director;
    }

    public String getWriter() {
        return this.writer;
    }

    public String getActors() {
        return this.actors;
    }

    public String getPlot() {
        return this.plot;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getCountry() {
        return this.country;
    }

    public String getAwards() {
        return this.awards;
    }

    public String getPoster() {
        return this.poster;
    }

    public List<Rating> getRatings() {
        return this.ratings;
    }

    public String getMetascore() {
        return this.metascore;
    }

    public String getImdbRating() {
        return this.imdbRating;
    }

    public String getImdbVotes() {
        return this.imdbVotes;
    }

    public String getImdbID() {
        return this.imdbID;
    }

    public String getType() {
        return this.type;
    }

    public String getDvd() {
        return this.dvd;
    }

    public String getBoxOffice() {
        return this.boxOffice;
    }

    public String getProduction() {
        return this.production;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getResponse() {
        return this.response;
    }

    public String getError() {
        return this.error;
    }

    @JsonProperty("Title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("Year")
    public void setYear(String year) {
        this.year = year;
    }

    @JsonProperty("Rated")
    public void setRated(String rated) {
        this.rated = rated;
    }

    @JsonProperty("Released")
    public void setReleased(String released) {
        this.released = released;
    }

    @JsonProperty("Runtime")
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    @JsonProperty("Genre")
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @JsonProperty("Director")
    public void setDirector(String director) {
        this.director = director;
    }

    @JsonProperty("Writer")
    public void setWriter(String writer) {
        this.writer = writer;
    }

    @JsonProperty("Actors")
    public void setActors(String actors) {
        this.actors = actors;
    }

    @JsonProperty("Plot")
    public void setPlot(String plot) {
        this.plot = plot;
    }

    @JsonProperty("Language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("Country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("Awards")
    public void setAwards(String awards) {
        this.awards = awards;
    }

    @JsonProperty("Poster")
    public void setPoster(String poster) {
        this.poster = poster;
    }

    @JsonProperty("Ratings")
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @JsonProperty("Metascore")
    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    @JsonProperty("imdbRating")
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    @JsonProperty("imdbVotes")
    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    @JsonProperty("imdbID")
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("DVD")
    public void setDvd(String dvd) {
        this.dvd = dvd;
    }

    @JsonProperty("BoxOffice")
    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    @JsonProperty("Production")
    public void setProduction(String production) {
        this.production = production;
    }

    @JsonProperty("Website")
    public void setWebsite(String website) {
        this.website = website;
    }

    @JsonProperty("Response")
    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty("Error")
    public void setError(String error) {
        this.error = error;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OMDBResponse other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (!Objects.equals(this$title, other$title)) return false;
        final Object this$year = this.getYear();
        final Object other$year = other.getYear();
        if (!Objects.equals(this$year, other$year)) return false;
        final Object this$rated = this.getRated();
        final Object other$rated = other.getRated();
        if (!Objects.equals(this$rated, other$rated)) return false;
        final Object this$released = this.getReleased();
        final Object other$released = other.getReleased();
        if (!Objects.equals(this$released, other$released)) return false;
        final Object this$runtime = this.getRuntime();
        final Object other$runtime = other.getRuntime();
        if (!Objects.equals(this$runtime, other$runtime)) return false;
        final Object this$genre = this.getGenre();
        final Object other$genre = other.getGenre();
        if (!Objects.equals(this$genre, other$genre)) return false;
        final Object this$director = this.getDirector();
        final Object other$director = other.getDirector();
        if (!Objects.equals(this$director, other$director)) return false;
        final Object this$writer = this.getWriter();
        final Object other$writer = other.getWriter();
        if (!Objects.equals(this$writer, other$writer)) return false;
        final Object this$actors = this.getActors();
        final Object other$actors = other.getActors();
        if (!Objects.equals(this$actors, other$actors)) return false;
        final Object this$plot = this.getPlot();
        final Object other$plot = other.getPlot();
        if (!Objects.equals(this$plot, other$plot)) return false;
        final Object this$language = this.getLanguage();
        final Object other$language = other.getLanguage();
        if (!Objects.equals(this$language, other$language)) return false;
        final Object this$country = this.getCountry();
        final Object other$country = other.getCountry();
        if (!Objects.equals(this$country, other$country)) return false;
        final Object this$awards = this.getAwards();
        final Object other$awards = other.getAwards();
        if (!Objects.equals(this$awards, other$awards)) return false;
        final Object this$poster = this.getPoster();
        final Object other$poster = other.getPoster();
        if (!Objects.equals(this$poster, other$poster)) return false;
        final Object this$ratings = this.getRatings();
        final Object other$ratings = other.getRatings();
        if (!Objects.equals(this$ratings, other$ratings)) return false;
        final Object this$metascore = this.getMetascore();
        final Object other$metascore = other.getMetascore();
        if (!Objects.equals(this$metascore, other$metascore)) return false;
        final Object this$imdbRating = this.getImdbRating();
        final Object other$imdbRating = other.getImdbRating();
        if (!Objects.equals(this$imdbRating, other$imdbRating))
            return false;
        final Object this$imdbVotes = this.getImdbVotes();
        final Object other$imdbVotes = other.getImdbVotes();
        if (!Objects.equals(this$imdbVotes, other$imdbVotes)) return false;
        final Object this$imdbID = this.getImdbID();
        final Object other$imdbID = other.getImdbID();
        if (!Objects.equals(this$imdbID, other$imdbID)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (!Objects.equals(this$type, other$type)) return false;
        final Object this$dvd = this.getDvd();
        final Object other$dvd = other.getDvd();
        if (!Objects.equals(this$dvd, other$dvd)) return false;
        final Object this$boxOffice = this.getBoxOffice();
        final Object other$boxOffice = other.getBoxOffice();
        if (!Objects.equals(this$boxOffice, other$boxOffice)) return false;
        final Object this$production = this.getProduction();
        final Object other$production = other.getProduction();
        if (!Objects.equals(this$production, other$production))
            return false;
        final Object this$website = this.getWebsite();
        final Object other$website = other.getWebsite();
        if (!Objects.equals(this$website, other$website)) return false;
        final Object this$response = this.getResponse();
        final Object other$response = other.getResponse();
        if (!Objects.equals(this$response, other$response)) return false;
        final Object this$error = this.getError();
        final Object other$error = other.getError();
        return Objects.equals(this$error, other$error);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OMDBResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $year = this.getYear();
        result = result * PRIME + ($year == null ? 43 : $year.hashCode());
        final Object $rated = this.getRated();
        result = result * PRIME + ($rated == null ? 43 : $rated.hashCode());
        final Object $released = this.getReleased();
        result = result * PRIME + ($released == null ? 43 : $released.hashCode());
        final Object $runtime = this.getRuntime();
        result = result * PRIME + ($runtime == null ? 43 : $runtime.hashCode());
        final Object $genre = this.getGenre();
        result = result * PRIME + ($genre == null ? 43 : $genre.hashCode());
        final Object $director = this.getDirector();
        result = result * PRIME + ($director == null ? 43 : $director.hashCode());
        final Object $writer = this.getWriter();
        result = result * PRIME + ($writer == null ? 43 : $writer.hashCode());
        final Object $actors = this.getActors();
        result = result * PRIME + ($actors == null ? 43 : $actors.hashCode());
        final Object $plot = this.getPlot();
        result = result * PRIME + ($plot == null ? 43 : $plot.hashCode());
        final Object $language = this.getLanguage();
        result = result * PRIME + ($language == null ? 43 : $language.hashCode());
        final Object $country = this.getCountry();
        result = result * PRIME + ($country == null ? 43 : $country.hashCode());
        final Object $awards = this.getAwards();
        result = result * PRIME + ($awards == null ? 43 : $awards.hashCode());
        final Object $poster = this.getPoster();
        result = result * PRIME + ($poster == null ? 43 : $poster.hashCode());
        final Object $ratings = this.getRatings();
        result = result * PRIME + ($ratings == null ? 43 : $ratings.hashCode());
        final Object $metascore = this.getMetascore();
        result = result * PRIME + ($metascore == null ? 43 : $metascore.hashCode());
        final Object $imdbRating = this.getImdbRating();
        result = result * PRIME + ($imdbRating == null ? 43 : $imdbRating.hashCode());
        final Object $imdbVotes = this.getImdbVotes();
        result = result * PRIME + ($imdbVotes == null ? 43 : $imdbVotes.hashCode());
        final Object $imdbID = this.getImdbID();
        result = result * PRIME + ($imdbID == null ? 43 : $imdbID.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $dvd = this.getDvd();
        result = result * PRIME + ($dvd == null ? 43 : $dvd.hashCode());
        final Object $boxOffice = this.getBoxOffice();
        result = result * PRIME + ($boxOffice == null ? 43 : $boxOffice.hashCode());
        final Object $production = this.getProduction();
        result = result * PRIME + ($production == null ? 43 : $production.hashCode());
        final Object $website = this.getWebsite();
        result = result * PRIME + ($website == null ? 43 : $website.hashCode());
        final Object $response = this.getResponse();
        result = result * PRIME + ($response == null ? 43 : $response.hashCode());
        final Object $error = this.getError();
        result = result * PRIME + ($error == null ? 43 : $error.hashCode());
        return result;
    }

    public String toString() {
        return "OMDBResponse(title=" + this.getTitle() + ", year=" + this.getYear() + ", rated=" + this.getRated() + ", released=" + this.getReleased() + ", runtime=" + this.getRuntime() + ", genre=" + this.getGenre() + ", director=" + this.getDirector() + ", writer=" + this.getWriter() + ", actors=" + this.getActors() + ", plot=" + this.getPlot() + ", language=" + this.getLanguage() + ", country=" + this.getCountry() + ", awards=" + this.getAwards() + ", poster=" + this.getPoster() + ", ratings=" + this.getRatings() + ", metascore=" + this.getMetascore() + ", imdbRating=" + this.getImdbRating() + ", imdbVotes=" + this.getImdbVotes() + ", imdbID=" + this.getImdbID() + ", type=" + this.getType() + ", dvd=" + this.getDvd() + ", boxOffice=" + this.getBoxOffice() + ", production=" + this.getProduction() + ", website=" + this.getWebsite() + ", response=" + this.getResponse() + ", error=" + this.getError() + ")";
    }

    static class Rating {
        @JsonProperty("Source")
        private String source;

        @JsonProperty("Value")
        private String value;

        @Override
        public String toString() {
            return "Rating{" +
                    "source='" + source + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

        public String getSource() {
            return this.source;
        }

        public String getValue() {
            return this.value;
        }

        @JsonProperty("Source")
        public void setSource(String source) {
            this.source = source;
        }

        @JsonProperty("Value")
        public void setValue(String value) {
            this.value = value;
        }
    }
}

