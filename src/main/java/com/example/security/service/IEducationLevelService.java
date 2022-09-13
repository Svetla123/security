package com.example.security.service;

import com.example.security.model.EducationLevel;

import java.util.List;

public interface IEducationLevelService {
    EducationLevel findEducationLevelById(Long id);
    List<EducationLevel> findAllEducationLevels();

    EducationLevel createEducationLevel(EducationLevel educationLevel);
    boolean deleteEducationLevel(long id);

    EducationLevel updateEducationLevel (long id, EducationLevel educationLevel);

    Long findEducationLevelByName(String name);
}