package com.example.wishlist.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.wishlist.repository.*;
import com.example.wishlist.entity.*;


import java.util.HashMap;
import java.util.ArrayList;

@RestController
public class WishlistController {
    private Authentication auth;

    @GetMapping("/")
    public Object getUserWishlists(@RequestParam String apiKey) {

        if (!auth.isUserValid(apiKey)) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("status", "failed");
            data.put("message", "Authentication required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
        }        
        HashMap<String, Object> wish = new HashMap<String, Object>();
        ArrayList<Object> wishlist = new ArrayList<Object>();
        wish.put("status", "Success");
        wish.put("name", "Grishon");
        wish.put("apiKey", apiKey);
        wishlist.add(wish);

        HashMap<String, Object> returnWish = new HashMap<String, Object>();
        returnWish.put("status", "Success");
        returnWish.put("data", wishlist);

        return returnWish;
    }
    
}
