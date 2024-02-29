package org.fiit.votefilm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.fiit.votefilm.util.FilmResultList;

@Data
public class TMDBResponse {
    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private FilmResultList results;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

}
