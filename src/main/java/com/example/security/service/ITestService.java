package com.example.security.service;

import com.example.security.model.Test;

import java.util.List;

public interface ITestService {
    Test findTestById(Long id);
    List<Test> findAllTests();

    boolean deleteTest (long id);

    Test createTest (Test test);

    Test updateTest (long id, Test test);

}
