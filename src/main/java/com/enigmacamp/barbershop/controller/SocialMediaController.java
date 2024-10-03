package com.enigmacamp.barbershop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.dto.request.SocialMediaRequest;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.dto.response.SocialMediaResponse;
import com.enigmacamp.barbershop.model.entity.SocialMedia;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.service.SocialMediaService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/barbers/social-media")
public class SocialMediaController {
    private final SocialMediaService socialMediaService;
    private final JwtHelpers jwtHelpers;

    @PostMapping("/current")
    public ResponseEntity<CommonResponse<SocialMediaResponse>> createSocialMedia(
            @RequestBody SocialMediaRequest request,
            HttpServletRequest srvrequest) {

        Users user = jwtHelpers.getUser(srvrequest);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (user.getBarbers() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbershop not found");
        }

        SocialMedia socialMedia = SocialMedia.builder()
                .platform_name(request.getPlatform_name())
                .barbershop_id(user.getBarbers())
                .platform_url(request.getPlatform_url())
                .build();

        socialMedia = socialMediaService.create(socialMedia);
        if (socialMedia == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create social media");
        }

        return ResponseEntity.ok(CommonResponse.<SocialMediaResponse>builder()
                .statusCode(200)
                .message("Social media created successfully")
                .data(socialMedia.toResponse())
                .build());
    }

    @PutMapping("/current")
    public ResponseEntity<CommonResponse<SocialMediaResponse>> updateSocialMedia(
            @RequestBody SocialMediaRequest request,
            HttpServletRequest srvrequest) {

        Users user = jwtHelpers.getUser(srvrequest);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (user.getBarbers() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbershop not found");
        }

        SocialMedia socialMedia = socialMediaService.getById(request.getSocialMediaId());

        if (socialMedia == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social media not found");
        }

        if (request.getPlatform_name() != null)
            socialMedia.setPlatform_name(request.getPlatform_name());
        if (request.getPlatform_url() != null)
            socialMedia.setPlatform_url(request.getPlatform_url());

        socialMedia = socialMediaService.update(socialMedia);
        if (socialMedia == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update social media");
        }

        return ResponseEntity.ok(CommonResponse.<SocialMediaResponse>builder()
                .statusCode(200)
                .message("Social media updated successfully")
                .data(socialMedia.toResponse())
                .build());
    }

    @DeleteMapping("/current/{socialMediaId}")
    public ResponseEntity<CommonResponse<String>> deleteSocialMedia(
            @PathVariable String socialMediaId,
            HttpServletRequest srvrequest) {

                Users user = jwtHelpers.getUser(srvrequest);
                if (user == null) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                }

                if (user.getBarbers() == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbershop not found");
                }

                SocialMedia socialMedia = socialMediaService.getById(socialMediaId);

                if (socialMedia == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social media not found");
                }

                if (socialMediaService.delete(socialMediaId)) {
                    return ResponseEntity.ok(CommonResponse.<String>builder()
                            .statusCode(200)
                            .message("Social media deleted successfully")
                            .data("Social media deleted successfully")
                            .build());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete social media");
                }
    }
}