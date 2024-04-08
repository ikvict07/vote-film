package org.fiit.votefilm.model;

import jakarta.persistence.*;
import org.fiit.votefilm.model.apiFilm.AbstractFilm;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * Entity representing a voting item.
 */
@Entity
public class VotingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "votes", nullable = false)
    private Long votes;

    @ManyToOne
    @JoinColumn(name = "voting_session_id")
    private VotingSession votingSession;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private AbstractFilm film;

    public VotingItem(String title, VotingSession votingSession, AbstractFilm film) {
        this.title = title;
        this.votingSession = votingSession;
        this.film = film;
    }

    public VotingItem() {

    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        VotingItem that = (VotingItem) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Long getVotes() {
        return this.votes;
    }

    public VotingSession getVotingSession() {
        return this.votingSession;
    }

    public AbstractFilm getFilm() {
        return this.film;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public void setVotingSession(VotingSession votingSession) {
        this.votingSession = votingSession;
    }

    public void setFilm(AbstractFilm film) {
        this.film = film;
    }

    public String toString() {
        return "VotingItem(id=" + this.getId() + ", title=" + this.getTitle() + ", votes=" + this.getVotes() + ", votingSession=" + this.getVotingSession() + ", film=" + this.getFilm() + ")";
    }
}
