package com.enigmacamp.barbershop.service.Impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.entity.SocialMedia;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.SocialMediaRepository;
import com.enigmacamp.barbershop.service.SocialMediaService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialMediaServiceImpl implements SocialMediaService {
    private final JwtHelpers jwtHelpers;
    private final SocialMediaRepository socialMediaRepository;

    @Override
    public SocialMedia create(HttpServletRequest srvrequest, SocialMedia request) {
        try {
            Users user = jwtHelpers.getUser(srvrequest);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }

            return socialMediaRepository.save(request);

        } catch (ResponseStatusException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SocialMedia create(SocialMedia request) {
        try {
            return socialMediaRepository.save(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SocialMedia update(SocialMedia request) {
        try {
            if (request.getSocial_media_id() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be null");
            }

            if (socialMediaRepository.findById(request.getSocial_media_id()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social media not found");
            }

            return socialMediaRepository.save(request);
        } catch (ResponseStatusException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String id) {
        try {
            socialMediaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SocialMedia getById(String id) {
        return socialMediaRepository.findById(id).orElse(null);
    }
}