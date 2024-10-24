package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Listing {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Boolean isDeactivated = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User author;

    @JsonProperty("authorId")
    public Long getAuthorId() {
        return author != null ? author.getId() : null;
    }

    public Listing() {
    }

    public Listing(Long id, String title, String description, User author, Boolean isDeactivated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.isDeactivated = isDeactivated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Boolean getDeactivated() {
        return isDeactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        isDeactivated = deactivated;
    }
}
