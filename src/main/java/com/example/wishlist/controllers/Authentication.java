package com.example.wishlist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import com.example.wishlist.entity.*;
import com.example.wishlist.helpers.*;
import com.example.wishlist.repository.*;

@RestController
public class Authentication {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody SignupForm form) {
        HashMap<String, Object> data = new HashMap<String, Object>();

        try {
            User newUser = new User();
            User existingUser = userRepository.findByUsername(form.getUsername());
            if (existingUser == null) {
                String uuid = UUID.randomUUID().toString();
                newUser.setUsername(form.getUsername());
                newUser.setPassword(form.getPassword());
                newUser.setKey(uuid);

                userRepository.save(newUser);
                data.put("status", "success");
                data.put("apiKey", uuid);
            }else{
                data.put("status", "error");
                data.put("message", "User with that username exists");
            }

            return data;
        } catch (Exception e) {
            data.put("status", "failed");
            data.put("message", "Something wrong happened");
            return data;
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginForm form) {
        User existingUser = userRepository.findByUsername(form.getUsername());
        HashMap<String, Object> data = new HashMap<String, Object>();

        if (existingUser != null && existingUser.getPassword().equals(form.getPassword())) {
            data.put("status", "success");
            data.put("apiKey", existingUser.getKey());

            return data;
        }else{
            data.put("status", "failed");
            data.put("message", "Username or password incorrect");

            return data;
        }
    }

    public Boolean isUserValid(String api_key){
        User existingUser = userRepository.findByApiKey(api_key);
        if (existingUser != null) {
            return true;
        }else{
            return false;
        }
    }
}
