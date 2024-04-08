package org.fiit.votefilm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fiit.votefilm.util.FilmResultList;

import java.util.Objects;


public class TMDBResponse {
    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private FilmResultList results;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

    public TMDBResponse() {
    }

    public int getPage() {
        return this.page;
    }

    public FilmResultList getResults() {
        return this.results;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getTotalResults() {
        return this.totalResults;
    }

    @JsonProperty("page")
    public void setPage(int page) {
        this.page = page;
    }

    @JsonProperty("results")
    public void setResults(FilmResultList results) {
        this.results = results;
    }

    @JsonProperty("total_pages")
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @JsonProperty("total_results")
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TMDBResponse other)) return false;
        if (!other.canEqual(this)) return false;
        if (this.getPage() != other.getPage()) return false;
        final Object this$results = this.getResults();
        final Object other$results = other.getResults();
        if (!Objects.equals(this$results, other$results)) return false;
        if (this.getTotalPages() != other.getTotalPages()) return false;
        return this.getTotalResults() == other.getTotalResults();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TMDBResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getPage();
        final Object $results = this.getResults();
        result = result * PRIME + ($results == null ? 43 : $results.hashCode());
        result = result * PRIME + this.getTotalPages();
        result = result * PRIME + this.getTotalResults();
        return result;
    }

    public String toString() {
        return "TMDBResponse(page=" + this.getPage() + ", results=" + this.getResults() + ", totalPages=" + this.getTotalPages() + ", totalResults=" + this.getTotalResults() + ")";
    }
}
