package com.enigmacamp.barbershop.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.constant.ResponseMessage;
import com.enigmacamp.barbershop.model.dto.request.GalleryImageRequest;
import com.enigmacamp.barbershop.model.dto.response.GalleryImageResponse;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.GalleryImage;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.GalleryImageRepository;
import com.enigmacamp.barbershop.service.BarberService;
import com.enigmacamp.barbershop.service.CloudinaryService;
import com.enigmacamp.barbershop.service.GalleryImageService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GalleryImageServiceImpl implements GalleryImageService {

    private final Path directoryPath;
    private final GalleryImageRepository galleryImageRepository;
    private final JwtHelpers jwtHelpers;
    private final BarberService barberService;
    private final String mainPath = "src/main/resources/static";
    private final String secondPath = "/assets/images/gallery-images";
    private final CloudinaryService cloudinaryService;

    @Autowired
    public GalleryImageServiceImpl(@Value(mainPath + secondPath) String directoryPath,
            GalleryImageRepository galleryImageRepository, JwtHelpers jwtHelpers, BarberService barberService,
            CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
        this.barberService = barberService;
        this.jwtHelpers = jwtHelpers;
        this.directoryPath = Paths.get(directoryPath);
        this.galleryImageRepository = galleryImageRepository;
    }

    @PostConstruct
    public void initDirectory() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @Override
    public List<GalleryImageResponse> saveGalleryImages(GalleryImageRequest images, HttpServletRequest srvrequest) {
        List<GalleryImageResponse> responses = new ArrayList<>();

        for (MultipartFile image : images.getImages()) {
            if (image.isEmpty())
                continue;

            try {
                if (!List.of("image/jpeg", "image/png", "image/jpg", "image/svg+xml")
                        .contains(image.getContentType()))
                    throw new ConstraintViolationException(ResponseMessage.ERROR_INVALID_CONTENT_TYPE, null);
                Users user = jwtHelpers.getUser(srvrequest);
                if (user == null) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                }

                Barbers barbers = barberService.getByEmail(user.getEmail());

                if (barbers == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
                }

                // String originalFilename = image.getOriginalFilename();
                // String contentType = image.getContentType();
                // System.out.println("=========================================");
                // System.out.println("originalFilename: " + originalFilename);
                // System.out.println(contentType);
                // System.out.println(contentType.substring(contentType.lastIndexOf('/') + 1));
                // System.out.println("=========================================");
                // // String extension =
                // originalFilename.substring(originalFilename.lastIndexOf('.'));
                // String extension = contentType.substring(contentType.lastIndexOf('/') + 1);

                // String uniqueFilename = UUID.randomUUID().toString() + "." + extension;
                // Path filePath = directoryPath.resolve(uniqueFilename);
                // Files.copy(image.getInputStream(), filePath);

                Map<String, String> uploadResult = cloudinaryService.uploadImage(image);

                GalleryImage galleryImage = GalleryImage.builder()
                        .name(uploadResult.get("display_name"))
                        .contentType(image.getContentType())
                        .size(image.getSize())
                        // .path(secondPath + "/" + uniqueFilename)
                        .path(uploadResult.get("url"))
                        .createdAt(System.currentTimeMillis())
                        .updatedAt(System.currentTimeMillis())
                        .barbersId(barbers)
                        .build();

                galleryImageRepository.saveAndFlush(galleryImage);

                GalleryImageResponse response = GalleryImageResponse.builder()
                        .name(galleryImage.getName())
                        .contentType(galleryImage.getContentType())
                        .size(galleryImage.getSize())
                        .path(galleryImage.getPath())
                        .barbers_id(barbers.getId())
                        .build();

                responses.add(response);

            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error saving file: " + e.getMessage());
            } catch (ConstraintViolationException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ndog " + e.getMessage());
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Unexpected error: " + e.getMessage());
            }
        }

        return responses;
    }

    @Override
    public Resource getById(String id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(String id) {
        try {
            if (!galleryImageRepository.existsById(id)) {
                return false;
            }

            galleryImageRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GalleryImage getByName(String name) {
        return null;
    }

    @Override
    public List<GalleryImageResponse> getByBarberId(String id) {
        try {
            Barbers barbers = barberService.getBarberById(id);
            if (barbers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
            }

            List<GalleryImage> galleryImages = galleryImageRepository.findAllByBarbersId(barbers);
            // if (galleryImages == null) {
            // return null;
            // }

            List<GalleryImageResponse> responses = new ArrayList<>();
            for (GalleryImage image : galleryImages) {
                GalleryImageResponse response = GalleryImageResponse.builder()
                        .image_id(image.getImage_id())
                        .name(image.getName())
                        .contentType(image.getContentType())
                        .size(image.getSize())
                        .path(image.getPath())
                        .barbers_id(barbers.getId())
                        .build();
                responses.add(response);
            }

            return responses;
        } catch (ResponseStatusException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GalleryImage getByIdEntity(String id) {
        try {
            return galleryImageRepository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}