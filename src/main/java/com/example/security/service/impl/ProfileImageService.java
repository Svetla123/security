package com.example.security.service.impl;

import com.example.security.model.ProfileImage;
import com.example.security.repository.ProfileImageRepository;
import com.example.security.service.IProfileImageService;
import com.example.security.service.impl.FileService.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ProfileImageService implements IProfileImageService {

    private ProfileImageRepository profileImageRepository;

    public ProfileImageService(ProfileImageRepository profileImageRepository) {
        super();
        this.profileImageRepository = profileImageRepository;
    }
    @Override
    public boolean deleteProfileImage(Long id) {
        try {
            this.profileImageRepository.deleteById(id);
        }
        catch (Exception e) {
            return false;
        }
        finally {
            return true;
        }
    }

    @Override
    public ProfileImage saveProfileImage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //mozda bolje sa setCompiledSolution(file.getBytes());
            ProfileImage newFile = new ProfileImage(fileName,
                    file.getContentType(),
                    file.getBytes());

            return profileImageRepository.save(newFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store image " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public ProfileImage findProfileImageById(Long id) {
        try {
            return this.profileImageRepository.findById(id).get();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProfileImage> findAllProfileImages() {
            return this.profileImageRepository.findAll();

    }
}
