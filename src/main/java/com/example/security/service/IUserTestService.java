package com.example.security.service;


import com.example.security.model.UserTest;

import java.util.List;

public interface IUserTestService {
    UserTest findUserTestById(Long id);
    List<UserTest> findAllUserTests();
    boolean deleteUserTest(Long id);
    UserTest createUserTest (UserTest userTest);
    UserTest updateUserTest (long id, UserTest userTest);

}
