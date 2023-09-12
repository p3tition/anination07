package com.example.anination05.repo;

import com.example.anination05.models.Anime_titles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Anime_titlesRepository extends JpaRepository<Anime_titles, Long> {

    @Query("SELECT a FROM Anime_titles a ORDER BY a.id")
    List<Anime_titles> findNextAnime(int offset, int limit);
}