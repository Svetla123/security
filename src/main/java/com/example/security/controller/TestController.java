package com.example.security.controller;
import com.example.security.model.Test;
import com.example.security.service.ITestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {
    private ITestService testService;

    public TestController(ITestService testService) {
        super();
        this.testService = testService;
    }

    @GetMapping("/api/tests")
    public List<Test> findAll() {
        List<Test> tests = this.testService.findAllTests();
        return tests;
    }

    @GetMapping("/api/tests/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Test test = this.testService.findTestById(id);
        if (test == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test with id " + id + " not found");
        }
        return ResponseEntity.ok(test);
    }

    @DeleteMapping("/api/tests/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result  = this.testService.deleteTest(id);

        if (result) {
            return ResponseEntity.ok("Test with id" + id + "deleted");
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test with id" + id + "not found");
        }
    }

    @PostMapping ("/api/tests")
    public ResponseEntity<?> create (@RequestBody Test test) {
        Test newTest = this.testService.createTest(test);
        return ResponseEntity.ok(newTest);
    }

    @PutMapping("/api/tests/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @RequestBody Test test) {
        Test updatedTest = this.testService.updateTest(id, test);
        if (updatedTest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Test with id " + id + " not found");
        }
        return ResponseEntity.ok(updatedTest);
    }
}
