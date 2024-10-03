package com.enigmacamp.barbershop.controller;

import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.entity.BarberProfilePicture;
import com.enigmacamp.barbershop.service.BarbersProfilePictureService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BarbersProfilePictureController {
    private final BarbersProfilePictureService barbersProfilePictureService;

    @PostMapping("/barber/profile-picture")
    public ResponseEntity<CommonResponse<BarberProfilePicture>> create(@RequestBody MultipartFile image, HttpServletRequest srvrequest) {
        BarberProfilePicture barbersProfilePicture = (BarberProfilePicture) barbersProfilePictureService.create(image, srvrequest);
        return ResponseEntity.ok(CommonResponse.<BarberProfilePicture>builder()
                .message("Barber profile picture uploaded successfully")
                .data(barbersProfilePicture)
                .build());
    }

}
