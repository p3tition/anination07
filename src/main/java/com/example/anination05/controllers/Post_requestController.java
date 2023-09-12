package com.example.anination05.controllers;

import com.example.anination05.BufferImage;
import com.example.anination05.FileSizeLimitExceededException;
import com.example.anination05.models.Post;
import com.example.anination05.models.Post_request;
import com.example.anination05.repo.Post_requestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

@Controller
public class Post_requestController {

    BufferImage bufferImage = new BufferImage();

    @GetMapping("/post_add_request")
    public String post_add_request(Model model, Principal principal) {
        return "post_add_request";
    }

    @Autowired
    private Post_requestRepository post_requestRepository;

    @PostMapping("/post_add_request")
    public String createPost(@RequestParam String title,
                             @RequestParam String main_text,
                             @RequestParam("avatar") MultipartFile avatarFile,
                             Model model, Principal principal) throws IOException {
        String author = principal.getName();

        Post_request post_request = new Post_request(title, main_text, author);

        if (!avatarFile.isEmpty()) {
            long maxFileSize = 4 * 1024 * 1024; // 4MB in bytes
            if (avatarFile.getSize() > maxFileSize) {
                throw new FileSizeLimitExceededException("File size exceeds the maximum limit (4MB).");
            }
            // Read the input image from the MultipartFile
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(avatarFile.getBytes()));

            // Check if the image is larger than the target size (960x540)
            boolean resizeImage = image.getWidth() > 1280 || image.getHeight() > 720;

            // Compress the image
            BufferedImage compressedImage;
            if (resizeImage) {
                // If the image is larger than the target size, resize it to the desired height while maintaining the aspect ratio
                int targetHeight = 720;
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
                String uploadDirectory = Paths.get(System.getProperty("user.dir"), "/src/main/upload/static/images/posts_pic/").toString();
                String filePath = uploadDirectory + File.separator + filename;

                File imageFile = new File(filePath);
                ImageIO.write(compressedImage, "jpg", imageFile);

                // Set the relative file path in the model (remove leading slash)
                String relativeFilePath = "/upload/static/images/posts_pic/" + filename;
                post_request.setPhotoPath(relativeFilePath);
            }
        }

        post_requestRepository.save(post_request);
        return "redirect:/users";
    }
}
