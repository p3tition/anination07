package com.example.anination05.controllers;

import com.example.anination05.models.Anime_titles;
import com.example.anination05.models.Post;
import com.example.anination05.repo.Anime_titlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class Anime_titlesController {

    @Autowired
    private Anime_titlesRepository animeRepository;

    @GetMapping("/anime")
    public String getAnimePage(Model model) {
        return "anime_titles";
    }

    @GetMapping("/load-anime")
    @ResponseBody
    public List<Anime_titles> loadMoreAnime(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Anime_titles> animePage = animeRepository.findAll(pageable);
        return animePage.getContent();
    }

    @GetMapping("/anime/{id}")
    public String animepage(@PathVariable(value = "id") Long id, Model model) {
        Anime_titles anime_title = animeRepository.findById(id).orElse(null);
        String pic_yt_api = anime_title.getTrailerYoutube();
        System.out.println(pic_yt_api);
        String video_id = null;
        Pattern pattern = Pattern.compile("watch\\?v=(.{11})");
        Matcher matcher = pattern.matcher(pic_yt_api);
        if (matcher.find()) {
            video_id = matcher.group(1);
        }
        System.out.println(video_id);
        if (anime_title == null) {
            return "error";
        }

        model.addAttribute("anime_title", anime_title);
        model.addAttribute("video_id", video_id);
        return "anime_title_template";
    }

}

