package com.example.anination05.controllers;

import com.example.anination05.models.Post;
import com.example.anination05.models.Users;
import com.example.anination05.repo.UserRepository;
import com.example.anination05.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/users")
    public String users(Model model) {
        Iterable<Users> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        Pageable pageable = PageRequest.of(0, 3);
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        for (Post post : posts) {
            post.getPhotoPath();
        }
        model.addAttribute("posts", posts);
        return "home";
    }

}