package com.example.anination05.controllers;

import com.example.anination05.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    @GetMapping("/admin_panel/data1")
    @ResponseBody
    public List<Map<String, Object>> getChartData1() {
        List<Map<String, Object>> chartData = new ArrayList<>();

        List<Object[]> result = userRepository.countUsersByCreatedAt();
        for (Object[] row : result) {
            LocalDate date = (LocalDate) row[0];
            Long userCount = (Long) row[1];

            Map<String, Object> data = new HashMap<>();
            data.put("createdAt", date);
            data.put("userCount", userCount);
            chartData.add(data);
        }

        return chartData;
    }
    @GetMapping("/admin_panel/data2")
    @ResponseBody
    public List<Map<String, Object>> getChartData2() {
        List<Map<String, Object>> chartData = new ArrayList<>();

        List<Object[]> userStats = userRepository.countUsersByCreatedAtWithOrder(); // Assuming you have a method to fetch user statistics by date

        Long cumulativeCount = 0L; // Variable to keep track of the cumulative count of users

        for (Object[] row : userStats) {
            LocalDate date = (LocalDate) row[0];
            Long userCount = (Long) row[1];

            cumulativeCount += userCount; // Add the current user count to the cumulative count

            Map<String, Object> data = new HashMap<>();
            data.put("date", date);
            data.put("userCount", cumulativeCount); // Use the cumulative count instead of the user count
            chartData.add(data);
        }

        return chartData;
    }

}
