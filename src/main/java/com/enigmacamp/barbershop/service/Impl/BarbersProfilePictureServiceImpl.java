package com.enigmacamp.barbershop.service.Impl;

import com.enigmacamp.barbershop.constant.ResponseMessage;
import com.enigmacamp.barbershop.model.entity.BarberProfilePicture;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.BarbersProfilePictureRepository;
import com.enigmacamp.barbershop.service.BarbersProfilePictureService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.repository.BarbersRepository;
import com.enigmacamp.barbershop.service.BarberService;
import com.enigmacamp.barbershop.service.CloudinaryService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BarbersProfilePictureServiceImpl implements BarbersProfilePictureService {
    private final Path directoryPath;
    private final BarbersProfilePictureRepository barbersProfilePictureRepository;
    private final JwtHelpers jwtHelpers;
    private final BarberService barberService;
    private final BarbersRepository barbersRepository;
    private final String mainPath = "src/main/resources/static";
    private final String secondPath = "/assets/images/barbershop";
    private final CloudinaryService cloudinaryService;

    @Autowired
    public BarbersProfilePictureServiceImpl(@Value(mainPath + secondPath) String directoryPath,
            BarbersProfilePictureRepository barbersProfilePictureRepository, JwtHelpers jwtHelpers,
            BarberService barberService, BarbersRepository barbersRepository, CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
        this.barbersRepository = barbersRepository;
        this.directoryPath = Paths.get(directoryPath);
        this.barbersProfilePictureRepository = barbersProfilePictureRepository;
        this.jwtHelpers = jwtHelpers;
        this.barberService = barberService;
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
    public BarberProfilePicture create(MultipartFile multipartFile, HttpServletRequest srvrequest) {
        try {
            if (!List.of("image/jpeg", "image/png", "image/jpg", "image/svg+xml")
                    .contains(multipartFile.getContentType()))
                throw new ConstraintViolationException(ResponseMessage.ERROR_INVALID_CONTENT_TYPE, null);

            Users user = jwtHelpers.getUser(srvrequest);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
            Barbers barbers = barberService.getByEmail(user.getEmail());

            if (barbers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            }

            Map<String, String> uploadResult = cloudinaryService.uploadImage(multipartFile);

            BarberProfilePicture barberProfilePicture = BarberProfilePicture.builder()
                    .name(uploadResult.get("display_name"))
                    .contentType(multipartFile.getContentType())
                    .size(multipartFile.getSize())
                    .path(uploadResult.get("url"))
                    .createdAt(System.currentTimeMillis())
                    .updatedAt(System.currentTimeMillis())
                    .build();

            barbersProfilePictureRepository.saveAndFlush(barberProfilePicture);

            barbers.setBarbershop_profile_picture_id(barberProfilePicture);

            barbersRepository.save(barbers);

            return barberProfilePicture;

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource getById(String id) {
        try {
            BarberProfilePicture image = barbersProfilePictureRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            BarberProfilePicture image = barbersProfilePictureRepository.findById(id)
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            Files.delete(filePath);
            barbersProfilePictureRepository.delete(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public BarberProfilePicture getByName(String name) {
        return barbersProfilePictureRepository.getByName(name);
    }
}
