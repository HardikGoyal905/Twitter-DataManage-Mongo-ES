package com.example.TwitterDataManageMongoES.controller;

import com.example.TwitterDataManageMongoES.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/followers")
public class Followers {
    @Autowired
    MainService service;
    @GetMapping("/twitter/save/es/{user}")
    public String saveFollowersFromTwitterToES(@PathVariable("user") String user){
        return service.storeTwitterFollowersIntoES(user);
    }

    @GetMapping("/fetch/es/groupbylocation/{user}")
    public HashMap<String, Integer> fetchFollowersfromESGroupByLocation(@PathVariable("user") String user){
        return service.fetchFanBaseInfoFromES(user);
    }
}