package com.example.anination05.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public String error(HttpServletRequest request) {
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        return "<h1>Error: " + errorMessage + "</h1>";
    }
    public String getErrorPath() {
        return "/error";
    }
}
