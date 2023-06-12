package com.example.anination05.controllers;

import com.example.anination05.models.Friends;
import com.example.anination05.models.Users;
import com.example.anination05.repo.UserRepository;
import com.example.anination05.repo.FriendRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;


    @PostMapping("/add_friend/{username}/to/{usr}")
    public String addFriend(@PathVariable("username") String username, @PathVariable("usr") String usr, Model model, HttpServletRequest request) {
        Users user = userRepository.findByUsername(username);
        Users friend = userRepository.findByUsername(usr);

        if (user == null || friend == null) {
            // Handle case where either user or friend is not found
            return "error";
        }

        Friends friends = new Friends();
        friends.setUser(user);
        friends.setFriend(friend);

        friendRepository.save(friends); // Assuming friendRepository is an instance of FriendRepository

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
