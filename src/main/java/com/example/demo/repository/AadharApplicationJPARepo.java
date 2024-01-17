package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.NewAadharApplicationObject;

import java.util.List;

@Repository
public interface AadharApplicationJPARepo extends JpaRepository<NewAadharApplicationObject, Integer> {
    List<NewAadharApplicationObject> findByApplicationId(int applicationId);
    List<NewAadharApplicationObject> findAll();
}
