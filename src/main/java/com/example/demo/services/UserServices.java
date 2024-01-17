package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pojo.UserObject;
import com.example.demo.repository.UserJPARepo;

import java.util.List;

@Service
public class UserServices {
    @Autowired
    UserJPARepo userRepo;

    private RestTemplate template= new RestTemplate();

    //add person
    public UserObject addUser(UserObject person) {
        return userRepo.save(person);
    }

    public UserObject updateUser(UserObject person) {
        return userRepo.save(person);
    }
 
    public List<UserObject> findByCitizenId(String citizenId) {
        return  userRepo.findByCitizenId(citizenId);
    }
}
