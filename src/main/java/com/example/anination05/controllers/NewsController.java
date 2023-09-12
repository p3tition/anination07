package com.example.anination05.controllers;

import com.example.anination05.models.Post;
import com.example.anination05.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/news")
    public String news(Model model) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        for (Post post : posts) {
            post.getPhotoPath();
        }

        model.addAttribute("posts", posts);
        return "news";
    }
}
