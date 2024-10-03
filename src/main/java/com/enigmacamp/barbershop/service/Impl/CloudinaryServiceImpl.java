package com.enigmacamp.barbershop.service.Impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.enigmacamp.barbershop.service.CloudinaryService;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> deleteImage(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }

}