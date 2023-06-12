package com.example.anination05.controllers;


import com.example.anination05.models.Users;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.anination05.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


@ControllerAdvice
@ComponentScan(basePackages = "com.example.anination05")
public class HeaderControllerAdvice {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addCurrentUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Users user = userRepository.findByUsername(username);
            if (user != null) {
                model.addAttribute("username", user.getUsername()); // Update the username attribute
                model.addAttribute("email", user.getEmail());
            }
        }

    }
}
