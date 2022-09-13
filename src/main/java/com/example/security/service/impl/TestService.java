package com.example.security.service.impl;

import com.example.security.model.Test;
import com.example.security.repository.TestRepository;
import com.example.security.service.ITestService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TestService implements ITestService {

    private TestRepository tests;

    private TestService(TestRepository tests) {
        super();
        this.tests = tests;
    }
    @Override
    public Test findTestById(Long id) {
        try{
            return this.tests.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Test> findAllTests() {
        return this.tests.findAll();
    }

    @Override
    public boolean deleteTest(long id) {
        Test test = this.findTestById(id);

        try {
            this.tests.delete(test);
        }
        catch (Exception e){
            return false;
        }finally {
            return true;
        }
    }

    @Override
    public Test createTest(Test test) {
        return this.tests.save(test);

    }

    @Override
    public Test updateTest(long id, Test test) {
        Test oldTest = this.tests.findById(id).get();

        if (oldTest == null){
            return null;
        }else {
            if (test.getName() != null) {
                oldTest.setName(test.getName());
            }
            if (test.getDeadline() != null){
                oldTest.setDeadline(test.getDeadline());
            }
            if (test.getPossiblePoints() != 0.0){
                oldTest.setPossiblePoints(test.getPossiblePoints());
            }
            if (test.getDescription() != null) {
                oldTest.setDescription(test.getDescription());
            }
        }
        return this.tests.save(oldTest);
    }
}
