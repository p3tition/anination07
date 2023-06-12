package com.example.anination05.controllers;

import com.example.anination05.models.Users;
import com.example.anination05.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{username}")
    public String userpage(@PathVariable(value = "username")String username, Model model) {
        Users user = userRepository.findByUsername(username);
        user.encodePhoto();
        if (user == null) {
            // handle the case when no user with the given username is found
            return "error";
        }

        model.addAttribute("user", user);
        return "user_template";
    }

    @GetMapping("/user/{username}/settings")
    @PreAuthorize("#username == authentication.principal.username")
    public String SettingsPage(Model model, @PathVariable String username) {
        // Only allow the authenticated user to view their own settings
        if (!username.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return"user_error_settings";
        }
        return "user_template_settings";
    }

    @PostMapping("/settings/avatar")
    public String saveAvatar(@RequestParam("avatar") MultipartFile avatarFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Users user = userRepository.findByUsername(username);
        if (!avatarFile.isEmpty()) {
            byte[] bytes = avatarFile.getBytes();
            user.setPhoto(bytes);
            userRepository.save(user);
        }
        return "redirect:/user/" + username;
    }

    @PostMapping("/settings/usr")
    public String saveUsername(@RequestParam("username") String username) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username_old = ((UserDetails) authentication.getPrincipal()).getUsername();
        Users user = userRepository.findByUsername(username_old);
        if (Objects.nonNull(username)) {
            user.setUsername(username);
            userRepository.save(user);
            Users updatedUser = userRepository.findByUsername(username);
            authentication = new UsernamePasswordAuthenticationToken(updatedUser, authentication.getCredentials(), authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        Users updatedUser = userRepository.findByUsername(username);
        return "redirect:/user/" + updatedUser.getUsername();
    }


    @PostMapping("/settings/email")
    public String saveEmail(@RequestParam("email") String email) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Users user = userRepository.findByUsername(username);
        if (Objects.nonNull(email)) {
            user.setEmail(email);
            userRepository.save(user);
        }
        return "redirect:/user/" + username;
    }
}
