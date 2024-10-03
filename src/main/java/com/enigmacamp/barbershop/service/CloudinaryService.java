package com.enigmacamp.barbershop.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    Map<String, String> uploadImage(MultipartFile file) throws IOException;

    Map<String, String> deleteImage(String publicId) throws IOException;
}