package com.example.anination05.models;

import jakarta.persistence.*;

@Entity
@Table(name = "anime_data")
public class Anime_titles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "photo_path", length = 255)
    private String photoPath;

    @Column(name = "title_japanese", length = 255)
    private String titleJapanese;

    @Column(name = "favorites")
    private Integer favorites;

    @Column(name = "broadcast_string", length = 255)
    private String broadcastString;

    @Column(name = "broadcast_timezone", length = 255)
    private String broadcastTimezone;

    @Column(name = "broadcast_time", length = 255)
    private String broadcastTime;

    @Column(name = "broadcast_day", length = 255)
    private String broadcastDay;

    @Column(name = "year_")
    private Integer year;

    @Column(name = "episodes")
    private Integer episodes;

    @Column(name = "rating", length = 255)
    private String rating;

    @Column(name = "scored_by")
    private Integer scoredBy;

    @Column(name = "title_src", length = 255)
    private String titleSrc;

    @Column(name = "type_", length = 50)
    private String type;

    @Column(name = "trailer_youtube", length = 100)
    private String trailerYoutube;

    @Column(name = "duration", length = 255)
    private String duration;

    @Column(name = "score", precision = 4, scale = 2)
    private Double score;

    @Column(name = "score_mal", precision = 4, scale = 2)
    private Double scoreMal;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "popularity")
    private Integer popularity;

    @Column(name = "members")
    private Integer members;

    @Column(name = "title_english", length = 255)
    private String titleEnglish;

    @Column(name = "season", length = 50)
    private String season;

    @Column(name = "airing")
    private boolean airing;

    @Column(name = "aired_string", length = 255)
    private String airedString;

    @Column(name = "aired_from", length = 50)
    private String airedFrom;

    @Column(name = "aired_to", length = 50)
    private String airedTo;

    @Column(name = "studios_id")
    private Integer studiosId;

    @Column(name = "title_default", length = 255)
    private String titleDefault;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "title_status", length = 50)
    private String titleStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getTitleJapanese() {
        return titleJapanese;
    }

    public void setTitleJapanese(String titleJapanese) {
        this.titleJapanese = titleJapanese;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public String getBroadcastString() {
        return broadcastString;
    }

    public void setBroadcastString(String broadcastString) {
        this.broadcastString = broadcastString;
    }

    public String getBroadcastTimezone() {
        return broadcastTimezone;
    }

    public void setBroadcastTimezone(String broadcastTimezone) {
        this.broadcastTimezone = broadcastTimezone;
    }

    public String getBroadcastTime() {
        return broadcastTime;
    }

    public void setBroadcastTime(String broadcastTime) {
        this.broadcastTime = broadcastTime;
    }

    public String getBroadcastDay() {
        return broadcastDay;
    }

    public void setBroadcastDay(String broadcastDay) {
        this.broadcastDay = broadcastDay;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getScoredBy() {
        return scoredBy;
    }

    public void setScoredBy(Integer scoredBy) {
        this.scoredBy = scoredBy;
    }

    public String getTitleSrc() {
        return titleSrc;
    }

    public void setTitleSrc(String titleSrc) {
        this.titleSrc = titleSrc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrailerYoutube() {
        return trailerYoutube;
    }

    public void setTrailerYoutube(String trailerYoutube) {
        this.trailerYoutube = trailerYoutube;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getScoreMal() {
        return scoreMal;
    }

    public void setScoreMal(Double scoreMal) {
        this.scoreMal = scoreMal;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public boolean isAiring() {
        return airing;
    }

    public void setAiring(boolean airing) {
        this.airing = airing;
    }

    public String getAiredString() {
        return airedString;
    }

    public void setAiredString(String airedString) {
        this.airedString = airedString;
    }

    public String getAiredFrom() {
        return airedFrom;
    }

    public void setAiredFrom(String airedFrom) {
        this.airedFrom = airedFrom;
    }

    public String getAiredTo() {
        return airedTo;
    }

    public void setAiredTo(String airedTo) {
        this.airedTo = airedTo;
    }

    public Integer getStudiosId() {
        return studiosId;
    }

    public void setStudiosId(Integer studiosId) {
        this.studiosId = studiosId;
    }

    public String getTitleDefault() {
        return titleDefault;
    }

    public void setTitleDefault(String titleDefault) {
        this.titleDefault = titleDefault;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTitleStatus() {
        return titleStatus;
    }

    public void setTitleStatus(String titleStatus) {
        this.titleStatus = titleStatus;
    }

    // Default constructor
    public Anime_titles() {
    }
}
