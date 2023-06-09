package com.example.anination05.controllers;

import com.example.anination05.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class Admin_panelController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/admin_panel")
    public String admin_panel(Model model, Principal principal){
        Authentication authentication = (Authentication) principal;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {


            return "admin_panel";
        } else {
            return "user_error_settings";
        }
    }

}
