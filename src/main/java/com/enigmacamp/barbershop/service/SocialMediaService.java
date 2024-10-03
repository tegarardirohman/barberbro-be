package com.enigmacamp.barbershop.service;

import com.enigmacamp.barbershop.model.entity.SocialMedia;

import jakarta.servlet.http.HttpServletRequest;

public interface SocialMediaService {
    SocialMedia create(HttpServletRequest srvrequest, SocialMedia request);

    SocialMedia create(SocialMedia request);

    SocialMedia update(SocialMedia request);

    Boolean delete(String id);

    SocialMedia getById(String id);
}