package com.example.anination05.controllers;

import com.example.anination05.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.anination05.repo.PostRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post_add")
    public String post_add(Model model, Principal principal) {
        Authentication authentication = (Authentication) principal;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
            return "post_add";
        } else {
            return "user_error_settings";
        }
    }

    @PostMapping("/post_add")
    public String createPost(@RequestParam String title,
                             @RequestParam String main_text,
                             @RequestParam("avatar") MultipartFile avatarFile,
                             Model model, Principal principal) throws IOException {
        String author = principal.getName();
        byte[] bytes = null;

        
        if (!avatarFile.isEmpty()) {
            bytes = avatarFile.getBytes();
        }
        Post post = new Post(title, main_text, author, bytes);
        postRepository.save(post);
        return "redirect:/users";
    }

    @GetMapping("/post/{id}")
    public String postpage(@PathVariable(value = "id") Long id, Model model) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            // handle the case when no post with the given id is found
            return "error";
        }

        post.encodePhoto();
        model.addAttribute("post", post);
        return "post_template";
    }
}
