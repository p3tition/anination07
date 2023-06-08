package com.example.anination05.controllers;

import com.example.anination05.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class admin_panel {

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
