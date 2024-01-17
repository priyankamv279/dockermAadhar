package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.pojo.*;
import com.example.demo.services.*;

import java.util.List;

@RestController
@RequestMapping("/AadharApp/citizens")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    AadharApplicationServices aadharApplicationServices;


    @PostMapping("/signUp")
    public ResponseEntity<Object> signUp(@RequestBody UserObject user){
        try {
            UserObject res = userServices.addUser(user);

            if(res != null) {
                return new ResponseEntity<Object>(res, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<Object>("There is some issue, please try again later.", HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<Object>("Facing some issue while trying to create your account, please try with different username.", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/logIn")
    public ResponseEntity<Object> signIn(@RequestBody UserLoginObject auth) {

        System.out.println(auth.getCitizenId() + " " + auth.getPassword());

        try {
            List<UserObject> user =  userServices.findByCitizenId(auth.getCitizenId());
            if(user.size() != 0) {
                if(user.get(0).getMobile().equals(auth.getPassword())){
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>("Incorrect password.",HttpStatus.UNAUTHORIZED);
                }
            }
            else {
                return new ResponseEntity<>("Incorrect citizenId.",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            System.out.println("exception is : " + e);
            return new ResponseEntity<Object>("There is some issue, please try again later.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/dashboard")
    public ResponseEntity<Object> dashboard(@RequestBody UserObject signedInUser) {
        System.out.println("Received request for dashboard with citizenId: " + signedInUser.getCitizenId());

    	try {
            List<UserObject> user =  userServices.findByCitizenId(signedInUser.getCitizenId());

            if(user.size() != 0) {
                    return new ResponseEntity<>(user, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Incorrect citizenId.",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            System.out.println("exception is : " + e);
            return new ResponseEntity<Object>("There is some issue, please try again later.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/issueAadhar")
    public ResponseEntity<Object> issueAadhar(@RequestBody NewAadharApplicationObject newApplication){
        newApplication.setDateOfTheApplication();
        System.out.println(newApplication.getCitizenId());
        try {
            NewAadharApplicationObject res = aadharApplicationServices.newApplication(newApplication);

            if(res != null) {
                List<UserObject> user =  userServices.findByCitizenId(newApplication.getCitizenId());
                if(user.size() != 0) {
                    user.get(0).setAadharApplied(true);
                    user.get(0).setPassportId(newApplication.getPassportId());
                    userServices.updateUser(user.get(0));   // updating the user entity that applied for aadhar
                }
                return new ResponseEntity<Object>(res, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<Object>("Facing some issue while trying to submit your new Application, please try after some time.", HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<Object>("There is some issue, may be you have already applied for aadhar.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateAadhar")
    public ResponseEntity<Object> updateAadhar(@RequestBody UserObject user) {
        try {
            // Find user(s) by citizen ID
            List<UserObject> users = userServices.findByCitizenId(user.getCitizenId());

            // Check if any user(s) were found
            if (!users.isEmpty()) {
                // Assuming you want to update the first user found, you can get it like this
                UserObject existingUser = users.get(0);

                // Update the existing user with the new values
                existingUser.setUserFullName(user.getUserFullName());
                existingUser.setEmail(user.getEmail());
                existingUser.setGender(user.getGender());
                existingUser.setAddress(user.getAddress());
                existingUser.setMobile(user.getMobile());
                existingUser.setPassportId(user.getPassportId());

                // Save the updated user
                UserObject res = userServices.updateUser(existingUser);

                // Check if the update was successful
                if (res != null) {
                    return new ResponseEntity<Object>(res, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<Object>("There is some issue, please try again later.", HttpStatus.NO_CONTENT);
                }
            } else {
                // Return a response indicating that the user was not found and HTTP status code 404 (Not Found)
                return new ResponseEntity<Object>("User not found with the given Citizen ID.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Return a response indicating a bad request with an error message and HTTP status code 400 (Bad Request)
            return new ResponseEntity<Object>("Facing some issue while trying to update your details, please try again.", HttpStatus.BAD_REQUEST);
        }
    }

}
