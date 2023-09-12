package com.example.anination05.controllers;

import com.example.anination05.models.Post;
import com.example.anination05.models.Post_request;
import com.example.anination05.models.Users;
import com.example.anination05.repo.PostRepository;
import com.example.anination05.repo.Post_requestRepository;
import com.example.anination05.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
public class Admin_panelController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private Post_requestRepository post_requestRepository;

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
    @GetMapping("/admin_panel/post_request")
    public String admin_panel_post_request(Model model, Principal principal){
        Authentication authentication = (Authentication) principal;
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {

        List<Post_request> posts = post_requestRepository.findAllByOrderByCreatedAtDesc();

        for (Post_request post_request : posts) {
            post_request.getPhotoPath();
        }

        model.addAttribute("posts", posts);
            return "post_request_lIST";
        } else {
            return "user_error_settings";
        }
    }

    @GetMapping("/admin_panel/post_request/{id}")
    public String postpagerequest(@PathVariable(value = "id") Long id, Model model) {
        Post_request post_request = post_requestRepository.findById(id).orElse(null);
        if (post_request == null) {
            // handle the case when no post with the given id is found
            return "error";
        }

        model.addAttribute("post", post_request);
        return "post_template_REQUEST";
    }

    @PostMapping("/admin_panel/post_request/{id}")
    public String handlePostRequestAction(@PathVariable(value = "id") Long id, @RequestParam String action,
                                          @RequestParam("postTitle") String postTitle,
                                          @RequestParam("postMaintext") String postMaintext,
                                          Model model, Principal principal) {
        Optional<Post_request> post_request = post_requestRepository.findById(id);
        String accept_by = principal.getName();
        if (post_request.isPresent()) {
            Post_request postRequest = post_request.get();

            if ("ADD".equals(action)) {
                Post post = new Post(postRequest.getTitle(), postRequest.getAuthor(), postRequest.getPhotoPath(), postRequest.getMain_text(), accept_by);
                postRepository.save(post);
                post_requestRepository.delete(postRequest);
            }else if ("CHANGE AND ADD".equals(action)) {
                Post post = new Post(postTitle, postRequest.getAuthor(), postRequest.getPhotoPath(), postMaintext, accept_by);
                postRepository.save(post);
                post_requestRepository.delete(postRequest);
            }else if ("DELETE".equals(action)) {
                post_requestRepository.delete(postRequest);
            }else{
                System.out.println("===== WRONG IN CHECKING WHAT TO DO =====");
            }
        }else{
            System.out.println("===== SOMETHING WRONG =====");
        }

        return "redirect:/"; // Redirect to the appropriate page after processing the action
    }
}
