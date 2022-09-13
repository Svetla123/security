package com.example.security.controller;

import com.example.security.model.EducationLevel;
import com.example.security.service.IEducationLevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EducationLevelController {
    private IEducationLevelService educationLevelService;

    public EducationLevelController(IEducationLevelService educationLevelService) {
        super();
        this.educationLevelService = educationLevelService;
    }

    @GetMapping("/api/educationlevels")
    public List<EducationLevel> findAll () {
        List<EducationLevel> educationLevels = this.educationLevelService.findAllEducationLevels();
        return educationLevels;
    }

    @GetMapping("/api/educationlevels/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        EducationLevel educationLevel = this.educationLevelService.findEducationLevelById(id);
        if (educationLevel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EducationLevel with id " + id + " not found");
        }
        return ResponseEntity.ok(educationLevel);
    }

    @DeleteMapping("/api/educationlevels/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result = this.educationLevelService.deleteEducationLevel(id);
        if (result) {
            return ResponseEntity.ok("EducationLevel with id " + id + " deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EducationLevel with id " + id + " not found");
    }

    @PostMapping ("/api/educationlevels")
    public ResponseEntity<?> create (@RequestBody EducationLevel educationLevel) {
        EducationLevel newEducationLevel = this.educationLevelService.createEducationLevel(educationLevel);
        return ResponseEntity.ok(newEducationLevel);
    }

    @PutMapping("/api/educationlevels/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @RequestBody EducationLevel educationLevel) {
        EducationLevel updatedEducationLevel = this.educationLevelService.updateEducationLevel(id, educationLevel);
        if (updatedEducationLevel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EducationLevel with id " + id + " not found");
        }
        return ResponseEntity.ok(updatedEducationLevel);
    }
}
