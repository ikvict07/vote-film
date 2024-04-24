package org.fiit.votefilm.model;

import jakarta.persistence.*;
import org.fiit.votefilm.model.users.VotingHost;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity representing a voting session.
 */
@Entity
public class VotingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "unique_code", nullable = false, unique = true)
    private String uniqueCode;
    @OneToMany(mappedBy = "votingSession")
    @Column(name = "voting_items")
    private List<VotingItem> votingItems;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private VotingHost creator;
    @Column(name = "is_open", nullable = false)
    private boolean isOpen;

    public VotingSession() {

    }


    public VotingSession(VotingHost creator, String title) {
        this.creator = creator;
        this.title = title;
        this.isOpen = true;
    }

    @PrePersist
    protected void generateUniqueCode() {
        uniqueCode = UUID.randomUUID().toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public List<VotingItem> getVotingItems() {
        return this.votingItems;
    }

    public void setVotingItems(List<VotingItem> votingItems) {
        this.votingItems = votingItems;
    }

    public VotingHost getCreator() {
        return this.creator;
    }

    public void setCreator(VotingHost creator) {
        this.creator = creator;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof VotingSession other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (!Objects.equals(this$title, other$title)) return false;
        final Object this$uniqueCode = this.getUniqueCode();
        final Object other$uniqueCode = other.getUniqueCode();
        if (!Objects.equals(this$uniqueCode, other$uniqueCode))
            return false;
        final Object this$votingItems = this.getVotingItems();
        final Object other$votingItems = other.getVotingItems();
        if (!Objects.equals(this$votingItems, other$votingItems))
            return false;
        final Object this$creator = this.getCreator();
        final Object other$creator = other.getCreator();
        if (!Objects.equals(this$creator, other$creator)) return false;
        return this.isOpen() == other.isOpen();
    }

    /**
     * Check if the object can be equal to this one.
     *
     * @param other The object to check.
     * @return True if the object can be equal to this one, false otherwise.
     */
    protected boolean canEqual(final Object other) {
        return other instanceof VotingSession;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $uniqueCode = this.getUniqueCode();
        result = result * PRIME + ($uniqueCode == null ? 43 : $uniqueCode.hashCode());
        final Object $votingItems = this.getVotingItems();
        result = result * PRIME + ($votingItems == null ? 43 : $votingItems.hashCode());
        final Object $creator = this.getCreator();
        result = result * PRIME + ($creator == null ? 43 : $creator.hashCode());
        result = result * PRIME + (this.isOpen() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "VotingSession(id=" + this.getId() + ", title=" + this.getTitle() + ", uniqueCode=" + this.getUniqueCode() + ", votingItems=" + this.getVotingItems() + ", creator=" + this.getCreator() + ", isOpen=" + this.isOpen() + ")";
    }
}
