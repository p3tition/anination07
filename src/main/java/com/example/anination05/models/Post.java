package com.example.anination05.models;

import jakarta.persistence.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Base64;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "main_text", columnDefinition="LONGTEXT")
    private String main_text;

    @Column(name = "author")
    private String author;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Lob
    @Column(name = "post_pic", columnDefinition="MEDIUMBLOB")
    private byte[] photo;

    @Transient
    private String base64EncodedImage;

    public String getBase64EncodedImage() {
        return base64EncodedImage;
    }
    public void setBase64EncodedImage(String base64EncodedImage) {
        this.base64EncodedImage = base64EncodedImage;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Post() {
    }

    public Post(String title, String main_text, String author, byte[] photo) {
        this.title = title;
        this.main_text = main_text;
        this.author = author;
        this.createdAt = LocalDate.now();
        this.photo = photo;
    }

    public void encodePhoto() {
        if (photo != null) {
            this.base64EncodedImage = Base64.getEncoder().encodeToString(photo);
        } else {
            // If the user hasn't uploaded an image, use the default image
            String defaultImageFile = "/static/img/news_element.png";
            InputStream is = getClass().getResourceAsStream(defaultImageFile);
            if (is != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                try {
                    while ((length = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, length);
                    }
                    this.base64EncodedImage = Base64.getEncoder().encodeToString(baos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // handle the case when the resource file is not found
                System.out.println("Default image file not found.");
            }
        }
    }
}
