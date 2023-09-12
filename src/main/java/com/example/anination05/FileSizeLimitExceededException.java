package com.example.anination05;

public class FileSizeLimitExceededException extends RuntimeException {
    public FileSizeLimitExceededException(String message) {
        super(message);
    }
}
