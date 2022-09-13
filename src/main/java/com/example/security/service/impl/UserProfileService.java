package com.example.security.service.impl;

import com.example.security.model.EducationLevel;
import com.example.security.model.UserProfile;
import com.example.security.repository.UserProfileRepository;
import com.example.security.service.IUserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService implements IUserProfileService {
    private UserProfileRepository userProfiles;
    private EducationLevelService educationLevelService;
    private UserProfileService(UserProfileRepository userProfiles, EducationLevelService educationLevelService) {
        super();
        this.userProfiles = userProfiles;
        this.educationLevelService = educationLevelService;
    }

    @Override
    public UserProfile findUserProfileById(Long id) {
        try{
            return this.userProfiles.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public List<UserProfile> findAllUserProfiles() {
        return this.userProfiles.findAll();
    }

    @Override
    public boolean deleteUserProfile(long id) {
        UserProfile userProfile = this.findUserProfileById(id);

        try {
            this.userProfiles.delete(userProfile);
        }
        catch (Exception e){
            return false;
        }
        finally {
            return true;
        }
    }

    @Override
    public UserProfile createUserProfile(UserProfile userProfile) {
        EducationLevel educationLevel = userProfile.getEducationLevel();
        String educationLevelName= educationLevel.getName();
        Long educationLevelId = this.educationLevelService.findEducationLevelByName(educationLevelName);
        educationLevel.setId(educationLevelId);
        userProfile.setEducationLevel(educationLevel);
        return this.userProfiles.save(userProfile);
    }

    @Override
    public UserProfile updateUserProfile(long id, UserProfile userProfile) {
        UserProfile oldUserProfile = this.findUserProfileById(id);

        if (oldUserProfile == null) {
            return null;
        } else {
            if (userProfile.getName() != null) {
                oldUserProfile.setName(userProfile.getName());
            }
            if (userProfile.getSurname() != null) {
                oldUserProfile.setSurname(userProfile.getSurname());
            }
            if (userProfile.getBirthDate() != null) {
                oldUserProfile.setBirthDate(userProfile.getBirthDate());
            }
            if (userProfile.getMobile() != null) {
                oldUserProfile.setMobile(userProfile.getMobile());
            }
            if (userProfile.getFaculty() != null) {
                oldUserProfile.setFaculty(userProfile.getFaculty());
            }
            if (userProfile.getResidence() != null) {
                oldUserProfile.setResidence(userProfile.getResidence());
            }
            if (userProfile.getEducationLevel() != null) {
                oldUserProfile.setEducationLevel(userProfile.getEducationLevel());
            }
            return this.userProfiles.save(oldUserProfile);
        }
    }
}
