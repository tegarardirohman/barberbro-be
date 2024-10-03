package com.enigmacamp.barbershop.service;

import com.enigmacamp.barbershop.model.entity.BarberProfilePicture;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

public interface BarbersProfilePictureService {
    BarberProfilePicture create(MultipartFile multipartFile, HttpServletRequest srvrequest);
    Resource getById(String id);
    void deleteById(String id);
    BarberProfilePicture getByName(String name);
}
