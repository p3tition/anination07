package com.example.anination05.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Transient
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Column(name = "photo_path", columnDefinition = "VARCHAR(255) DEFAULT '/static/img/10.png'")
    private String photoPath;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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
