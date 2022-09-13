package com.example.security.controller;

import com.example.security.model.AppModel;
import com.example.security.model.ProfileImage;
import com.example.security.model.Response;
import com.example.security.service.IProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class ProfileImageController extends AppModel {

    @Autowired
    private IProfileImageService profileImageService;
    public ProfileImageController(IProfileImageService profileImageService) {
        super();
        this.profileImageService = profileImageService;
    }

    @GetMapping("/api/profileImages/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        ProfileImage profileImage = this.profileImageService.findProfileImageById(id);
        if (profileImage == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image with id " + id + " not found");
        }
        return ResponseEntity.ok(profileImage);
    }

    @GetMapping("/api/profileImages")
    public List<ProfileImage> findAll() {
        List <ProfileImage> profileImages = this.profileImageService.findAllProfileImages();
        return profileImages;
    }

    @DeleteMapping("/api/profileImages/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result  = this.profileImageService.deleteProfileImage(id);
        if (result) {
            return ResponseEntity.ok("Image with id " + id + " deleted");
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image with id " + id + " not found");
        }
    }

    @PostMapping("/api/profileImages")
    public Response create(@RequestParam("file") MultipartFile file) {
        ProfileImage profileImage = profileImageService.saveProfileImage(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/profileImageDownload/")
                .path(profileImage.getFileName())
                .toUriString();
        return new Response(profileImage.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
}
