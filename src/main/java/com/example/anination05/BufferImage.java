package com.example.anination05;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferImage {
    public BufferedImage resizeAndCompressImage(BufferedImage image, int targetWidth, int targetHeight, float quality) {
        // If the image's width is smaller than the target width, use the original image's dimensions
        if (image.getWidth() < targetWidth) {
            targetWidth = image.getWidth();
            targetHeight = image.getHeight();
        }

        // Resize the image
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics.dispose();

        // Compress the image to a specific quality
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(resizedImage, null, null), param);
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert compressed image bytes to BufferedImage
        byte[] compressedImageBytes = baos.toByteArray();
        try {
            return ImageIO.read(new ByteArrayInputStream(compressedImageBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
