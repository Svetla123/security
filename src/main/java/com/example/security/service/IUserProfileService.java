package com.example.security.service;


import com.example.security.model.UserProfile;

import java.util.List;

public interface IUserProfileService {
    UserProfile findUserProfileById(Long id);
    List<UserProfile> findAllUserProfiles();
    boolean deleteUserProfile (long id);
    UserProfile createUserProfile (UserProfile userProfile);
    UserProfile updateUserProfile (long id, UserProfile userProfile);
}
