package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.UserObject;

import java.util.List;

@Repository
public interface UserJPARepo extends JpaRepository<UserObject, Integer> {
    //List<UserObject> findByName(String Username);
    List<UserObject> findByUserId(int userId);
    List<UserObject> findByCitizenId(String citizenId);
}