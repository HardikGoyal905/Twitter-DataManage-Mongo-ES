package com.example.TwitterDataManageMongoES.controller;

import com.example.TwitterDataManageMongoES.services.MainService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/tweets")
public class Tweets {
    @Autowired
    MainService service;

    @GetMapping("/twitter/save/es/{user}")
    public String saveTweetsFromTwitterToES(@PathVariable("user") String user){
        return service.storeTwitterTweetsIntoES(user);
    }

    @GetMapping("/fetch/es/datehistogram")
    public HashMap<String, LinkedHashMap<String, Integer>> fetchTweetsfromES(){
        return service.fetchDateHistogramTweetsDatafromES();
    }
}