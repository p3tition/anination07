package com.example.anination05.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "about")
    private String about;

    @Column(name = "role")
    private String role;

    @Column(name = "created_at")
    private LocalDate createdAt;

    private boolean accountNonLocked;
    private boolean isAccountNonExpired;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @Lob
    @Column(name = "user_pic", columnDefinition="MEDIUMBLOB")
    private byte[] photo;

    @Transient
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transient
    private String base64EncodedImage;

    public String getBase64EncodedImage() {
        return base64EncodedImage;
    }
    public void setBase64EncodedImage(String base64EncodedImage) {
        this.base64EncodedImage = base64EncodedImage;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    // Add a null check for the photo field
    public void encodePhoto() {
        if (photo != null) {
            this.base64EncodedImage = Base64.getEncoder().encodeToString(photo);
        } else {
            // If the user hasn't uploaded an image, use the default image
            String defaultImageFile = "/static/img/10.png";
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

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Users() {
    }

    public Users(String username, String password, String email, String role) {
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.email = email;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
