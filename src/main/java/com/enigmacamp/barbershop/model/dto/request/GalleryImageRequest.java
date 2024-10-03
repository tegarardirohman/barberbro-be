package com.enigmacamp.barbershop.model.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class GalleryImageRequest {
    private List<MultipartFile> images;
}