package com.example.security.controller;

import com.example.security.model.UserTest;
import com.example.security.service.IUserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserTestController {
    @Autowired
    private IUserTestService userTestService;
    public UserTestController(IUserTestService userTestService) {
        super();
        this.userTestService = userTestService;
    }

    @GetMapping("/api/userTests")
    public List<UserTest> findAll() {
        List <UserTest> userTests = this.userTestService.findAllUserTests();
        return userTests;
    }

    @GetMapping("/api/userTests/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        UserTest userTest = this.userTestService.findUserTestById(id);
        if (userTest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User test with id " + id + " not found");
        }
        return ResponseEntity.ok(userTest);
    }

    @DeleteMapping("/api/userTests/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result  = this.userTestService.deleteUserTest(id);
        if (result) {
            return ResponseEntity.ok("User test with id " + id + " deleted");
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User test test with id " + id + " not found");
        }
    }

    @PostMapping("/api/userTests")
    public ResponseEntity<?> create (@RequestBody UserTest userTest) {
        UserTest newUserTest = this.userTestService.createUserTest(userTest);
        return ResponseEntity.ok(newUserTest);
    }

    @PutMapping ("/api/userTests/{id}")
    public ResponseEntity<?> update (@RequestBody UserTest userTest, @PathVariable Long id) {
        UserTest updatedUserTest = this.userTestService.updateUserTest(id,userTest);
        if (updatedUserTest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User test with id " + id + " not found");
        }
        return ResponseEntity.ok(updatedUserTest);
    }
}
