package com.example.security.repository;

import com.example.security.model.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long> {
    EducationLevel findByName(String name);
}
