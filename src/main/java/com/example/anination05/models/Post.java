package com.example.anination05.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "main_text", columnDefinition="LONGTEXT")
    private String main_text;

    @Column(name = "accept_by")
    private String accept_by;

    @Column(name = "author")
    private String author;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "photo_path", columnDefinition = "VARCHAR(255) DEFAULT '/static/img/news_element.png'")
    private String photoPath;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

    public String getMain_text() {
        return main_text;
    }

    public void setMain_text(String main_text) {
        this.main_text = main_text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccept_by() {
        return accept_by;
    }

    public void setAccept_by(String accept_by) {
        this.accept_by = accept_by;
    }

    public Post() {
    }

    public Post(String title, String main_text, String author) {
        this.title = title;
        this.main_text = main_text;
        this.author = author;
        this.createdAt = LocalDate.now();
    }

    public Post(String title, String main_text, String author, String accept_by) {
        this.title = title;
        this.main_text = main_text;
        this.author = author;
        this.createdAt = LocalDate.now();
        this.accept_by = accept_by;
    }
    public Post(String title, String author, String photoPath, String mainText, String acceptBy) {
        this.title = title;
        this.author = author;
        this.photoPath = photoPath;
        this.main_text = mainText;
        this.createdAt = LocalDate.now();
        this.accept_by = acceptBy;
    }
}
