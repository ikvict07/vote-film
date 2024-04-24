package org.fiit.votefilm.model.apiFilm;


/**
 * Interface for a film.
 */
public interface Film {
    Long getId();

    void setId(Long id);

    String getTitle();

    void setTitle(String title);

    String getLanguage();

    void setLanguage(String language);

    String getDescription();

    void setDescription(String description);

    String getPoster();

    void setPoster(String poster);
}
