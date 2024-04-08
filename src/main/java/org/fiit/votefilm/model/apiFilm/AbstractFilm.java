package org.fiit.votefilm.model.apiFilm;

import jakarta.persistence.*;
import org.fiit.votefilm.model.VotingItem;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public abstract class AbstractFilm implements Film, Serializable {
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
    @Transient
    private List<VotingItem> votingItems;

    public AbstractFilm() {
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AbstractFilm that = (AbstractFilm) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPoster() {
        return this.poster;
    }

    public List<VotingItem> getVotingItems() {
        return this.votingItems;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setVotingItems(List<VotingItem> votingItems) {
        this.votingItems = votingItems;
    }

    public String toString() {
        return "AbstractFilm(id=" + this.getId() + ", title=" + this.getTitle() + ", language=" + this.getLanguage() + ", description=" + this.getDescription() + ", poster=" + this.getPoster() + ")";
    }
}
