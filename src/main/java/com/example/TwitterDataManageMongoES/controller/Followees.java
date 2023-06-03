package com.example.TwitterDataManageMongoES.controller;

import com.example.TwitterDataManageMongoES.services.MainService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/followees")
public class Followees {
    @Autowired
    MainService service;

    @GetMapping("/twitter/save/mongo/{user}")
    public String saveFolloweesFromTwitterToMongo(@PathVariable("user") String user){
        return service.storeTwitterFolloweesIntoMongo(user);
    }

    @GetMapping("/fetch/mongo/sortbyfollowerscount/{user}")
    public List<Document> fetchFolloweesfromMongo(@PathVariable("user") String user){
        return service.fetchFamousFolloweesFromMongo(user);
    }
}
