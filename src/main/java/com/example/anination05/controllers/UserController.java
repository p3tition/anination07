package com.example.anination05.controllers;

import com.example.anination05.BufferImage;
import com.example.anination05.FileSizeLimitExceededException;
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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    BufferImage bufferImage = new BufferImage();
    @GetMapping("/user/{username}")
    public String userpage(@PathVariable(value = "username") String username, Model model) {
        Users user = userRepository.findByUsername(username);
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
            long maxFileSize = 4 * 1024 * 1024; // 4MB in bytes
            if (avatarFile.getSize() > maxFileSize) {
                throw new FileSizeLimitExceededException("File size exceeds the maximum limit (4MB).");
            }
            // Read the input image from the MultipartFile
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(avatarFile.getBytes()));

            // Check if the image is larger than the target size
            boolean resizeImage = image.getWidth() > 250 || image.getHeight() > 250;

            // Compress the image
            BufferedImage compressedImage;
            if (resizeImage) {
                // If the image is larger than the target size, resize it to the desired height while maintaining the aspect ratio
                int targetHeight = 250;
                int targetWidth = (int) Math.ceil((double) image.getWidth() / image.getHeight() * targetHeight);
                compressedImage = bufferImage.resizeAndCompressImage(image, targetWidth, targetHeight, 1);
            } else {
                // If the image is smaller than the target size, compress it without changing the resolution
                compressedImage = bufferImage.resizeAndCompressImage(image, image.getWidth(), image.getHeight(), 1);
            }

            if (compressedImage != null) {
                // Generate a unique filename for the user's avatar
                String filename = UUID.randomUUID().toString() + ".jpg";

                // Set the file path where the avatar will be stored
                String uploadDirectory = Paths.get(System.getProperty("user.dir"), "/src/main/upload/static/images/users_pic/").toString();
                String filePath = uploadDirectory + File.separator + filename;

                File imageFile = new File(filePath);
                ImageIO.write(compressedImage, "jpg", imageFile);

                // Set the relative file path in the model (remove leading slash)
                String relativeFilePath = "/upload/static/images/users_pic/" + filename;
                user.setPhotoPath(relativeFilePath);
            }
        }
        userRepository.save(user);
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
