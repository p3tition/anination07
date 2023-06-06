package com.example.anination05.controllers;

import com.example.anination05.models.Post;
import com.example.anination05.models.User;
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
    public String users(Model model){
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            user.encodePhoto(); // Call the encodePhoto method to set the base64EncodedImage property
        }
        model.addAttribute("users",users);
        return "users";
    }

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        Pageable pageable = PageRequest.of(0, 4);
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        for (Post post : posts) {
            post.encodePhoto();
        }
        model.addAttribute("posts", posts);
        return "home";
    }

}