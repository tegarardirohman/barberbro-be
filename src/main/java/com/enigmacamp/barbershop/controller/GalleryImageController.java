package com.enigmacamp.barbershop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.dto.request.GalleryImageRequest;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.dto.response.GalleryImageResponse;
import com.enigmacamp.barbershop.service.GalleryImageService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GalleryImageController {
    private final GalleryImageService galleryImageService;

    @PostMapping("/gallery-image")
    public ResponseEntity<CommonResponse<List<GalleryImageResponse>>> create(@RequestBody GalleryImageRequest request,
            HttpServletRequest srvrequest) {
        List<GalleryImageResponse> galleryImages = galleryImageService.saveGalleryImages(request, srvrequest);

        return ResponseEntity.ok(CommonResponse.<List<GalleryImageResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Images uploaded successfully")
                .data(galleryImages)
                .build());
    }

    @GetMapping("barbers/{id}/gallery-images")
    public ResponseEntity<CommonResponse<List<GalleryImageResponse>>> getByBarberId(@PathVariable String id) {

        List<GalleryImageResponse> galleryImages = galleryImageService.getByBarberId(id);
        if (galleryImages != null) {
            return ResponseEntity.ok(CommonResponse.<List<GalleryImageResponse>>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Images fetched successfully")
                    .data(galleryImages)
                    .build());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("gallery-image/{id}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String id) {
        
        if (galleryImageService.deleteById(id)) {
            return ResponseEntity.ok(CommonResponse.<String>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Image deleted successfully")
                    .data("Image deleted successfully")
                    .build());
        }
        
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
    }
}