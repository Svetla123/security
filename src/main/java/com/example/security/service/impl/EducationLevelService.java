package com.example.security.service.impl;

import com.example.security.model.EducationLevel;
import com.example.security.repository.EducationLevelRepository;
import com.example.security.service.IEducationLevelService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EducationLevelService implements IEducationLevelService {

    private EducationLevelRepository educations;

    private EducationLevelService(EducationLevelRepository educations) {
        super();
        this.educations = educations;
    }

    @Override
    public EducationLevel findEducationLevelById(Long id) {
        try{
            return this.educations.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EducationLevel> findAllEducationLevels() {
        return this.educations.findAll();
    }

    @Override
    public boolean deleteEducationLevel(long id){
        EducationLevel e = this.findEducationLevelById(id);
        try {
            this.educations.delete(e);
        }
        catch (Exception ex) {
            return false;
        }finally {
            return true;
        }
    }

    @Override
    public EducationLevel createEducationLevel(EducationLevel educationLevel) {
        return this.educations.save(educationLevel);
    }

    @Override
    public EducationLevel updateEducationLevel (long id, EducationLevel educationLevel) {
        EducationLevel oldEducation = this.findEducationLevelById(id);

        if (educationLevel == null) {
            return null;
        } else {
            if (educationLevel.getName() != null) {
                oldEducation.setName(educationLevel.getName());
            }
            if (educationLevel.getAbbreviation() != null) {
                oldEducation.setAbbreviation(educationLevel.getAbbreviation());
            }
        }
        return this.educations.save(oldEducation);
    }

    @Override
    public Long findEducationLevelByName(String name) {
        return this.educations.findByName(name).getId();
    }
}
