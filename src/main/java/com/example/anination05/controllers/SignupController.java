package com.example.anination05.controllers;

import com.example.anination05.models.Users;
import com.example.anination05.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String signup(Model model){
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             Model model){
        String role = "USER";
        Users user = new Users(username, email, password, role);
        userRepository.save(user);
        return "redirect:/users";
    }

}
