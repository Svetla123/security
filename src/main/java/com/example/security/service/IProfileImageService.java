package com.example.security.service;

import com.example.security.model.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProfileImageService {
    boolean deleteProfileImage(Long id);

    ProfileImage saveProfileImage(MultipartFile file);

    ProfileImage findProfileImageById(Long id);
    List<ProfileImage> findAllProfileImages();
}
