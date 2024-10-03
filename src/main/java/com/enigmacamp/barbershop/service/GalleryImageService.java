package com.enigmacamp.barbershop.service;

import java.util.List;

import org.springframework.core.io.Resource;

import com.enigmacamp.barbershop.model.dto.request.GalleryImageRequest;
import com.enigmacamp.barbershop.model.dto.response.GalleryImageResponse;
import com.enigmacamp.barbershop.model.entity.GalleryImage;

import jakarta.servlet.http.HttpServletRequest;

public interface GalleryImageService {
    List<GalleryImageResponse> saveGalleryImages(GalleryImageRequest images, HttpServletRequest srvrequest);

    Resource getById(String id);

    GalleryImage getByIdEntity(String id);

    Boolean deleteById(String id);

    GalleryImage getByName(String name);

    List<GalleryImageResponse> getByBarberId(String id);
}