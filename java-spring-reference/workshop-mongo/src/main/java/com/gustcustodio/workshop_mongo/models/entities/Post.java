package com.gustcustodio.workshop_mongo.models.entities;

import com.gustcustodio.workshop_mongo.models.embedded.Author;
import com.gustcustodio.workshop_mongo.models.embedded.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    private Instant moment;

    private String title;

    private String body;

    private Author author;
    
    private List<Comment> comments = new ArrayList<>();

    public Post(String id, Instant moment, String title, String body, Author author) {
        this.id = id;
        this.moment = moment;
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
