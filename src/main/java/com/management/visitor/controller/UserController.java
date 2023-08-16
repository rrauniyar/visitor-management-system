package com.management.visitor.controller;

import com.management.visitor.exception.ResourceNotFoundException;
import com.management.visitor.model.User;
import com.management.visitor.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @Operation(summary = "This is to fetch All the User stored in Db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched All the User from Db",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "NOt Available",
                    content = @Content)
    })
    @GetMapping
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    //build create user REST API

    @Operation(summary = "This is to post User details which is stored in Db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User added successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Failed to add User details you provided",
                    content = @Content)
    })
    @PostMapping
//    @RequestBody converts json into java object i.e user details object
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    //build get user by id REST API
    @Operation(summary = "Get user by id")
    @ApiResponse()
    @GetMapping("{id}")
    public ResponseEntity<User> getUserId(@PathVariable long id){
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User doesn't exist with id "+id));
        return ResponseEntity.ok(user);
    }

    //build update user REST API
    //response entity helps to construct response object
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails){
        User updateUser = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not exist with id : "+id));
        updateUser.setFirstName(userDetails.getFirstName());
        updateUser.setLastName(userDetails.getLastName());
        updateUser.setEmailId(userDetails.getEmailId());
        updateUser.setContactNumber(userDetails.getContactNumber());
        updateUser.setPurposeOfVisit(userDetails.getPurposeOfVisit());

        userRepository.save(updateUser);
        return ResponseEntity.ok(updateUser);
    }

    //build delete user REST API
    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id){
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not exist with this id: "+id));
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
