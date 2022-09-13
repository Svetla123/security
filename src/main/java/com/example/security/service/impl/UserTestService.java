package com.example.security.service.impl;

import com.example.security.model.UserTest;
import com.example.security.repository.UserTestRepository;
import com.example.security.service.IUserTestService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserTestService implements IUserTestService {

    private UserTestRepository userTests;
    public UserTestService(UserTestRepository userTests) {
        super();
        this.userTests = userTests;
    }

    @Override
    public UserTest findUserTestById(Long id) {
        try{
            return this.userTests.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserTest> findAllUserTests() {
        return this.userTests.findAll();
    }

    @Override
    public boolean deleteUserTest(Long id) {
        UserTest userTest = this.findUserTestById(id);

        try {
            this.userTests.delete(userTest);
        }
        catch (Exception e){
            return false;
        }
        finally {
            return true;
        }
    }

    @Override
    public UserTest createUserTest(UserTest userTest) {
        return this.userTests.save(userTest);
    }

    @Override
    public UserTest updateUserTest(long id, UserTest userTest) {
        UserTest oldUserTest = this.findUserTestById(id);

        if (oldUserTest == null) {
            return null;
        } else {
            if (userTest.getAchivedPoints() != 0.0) {
                oldUserTest.setAchivedPoints(userTest.getAchivedPoints());
            }
            if (userTest.getTest() != null) {
                oldUserTest.setTest(userTest.getTest());
            }
            if (userTest.getUser() != null) {
                oldUserTest.setUser(userTest.getUser());
            }
            if (userTest.getCorrector() != null) {
                oldUserTest.setCorrector(userTest.getCorrector());
            }
            return this.userTests.save(oldUserTest);
        }
    }
}
