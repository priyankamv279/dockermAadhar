package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.pojo.NewAadharApplicationObject;
import com.example.demo.repository.AadharApplicationJPARepo;

import java.util.List;

@Service
public class AadharApplicationServices {
    @Autowired
    AadharApplicationJPARepo aadharApplicationRepo;

    public NewAadharApplicationObject newApplication(NewAadharApplicationObject application) {
        return aadharApplicationRepo.save(application);
    }

    public List<NewAadharApplicationObject> findByApplicationId(int applicationId) {
        return  aadharApplicationRepo.findByApplicationId(applicationId);
    }

    public List<NewAadharApplicationObject> findByAllApplications() {
        return  aadharApplicationRepo.findAll();
    }
    public void deleteApprovedApplication(int applicationId) {
        List<NewAadharApplicationObject> approvedApplication = aadharApplicationRepo.findByApplicationId(applicationId);
        aadharApplicationRepo.delete(approvedApplication.get(0));
    }
}
