package com.example.TwitterDataManageMongoES.services;

import com.example.TwitterDataManageMongoES.dataServices.ElasticFetch;
import com.example.TwitterDataManageMongoES.dataServices.ElasticStore;
import com.example.TwitterDataManageMongoES.dataServices.MongoService;
import com.example.TwitterDataManageMongoES.dataServices.TwitterFetch;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class MainService {
    @Autowired
    TwitterFetch twitterFetch;

    @Autowired
    MongoService mongoService;

    @Autowired
    ElasticFetch elasticFetch;

    @Autowired
    ElasticStore elasticStore;


    public String storeTwitterFolloweesIntoMongo(String user){
        List<TwitterProfile> followees=twitterFetch.fetchFollowees(user);
        mongoService.storeFollowees(followees, user);

        return followees.size()+" Followees Stored";
    }

    public String storeTwitterFollowersIntoES(String user){
        List<TwitterProfile> followers= twitterFetch.fetchFollowers(user);
        elasticStore.storeFollowers(followers, user);

        return followers.size()+" Followers Stored";
    }

    public String storeTwitterTweetsIntoES(String user){
        List<Tweet> tweets=twitterFetch.fetchTweets(user);
        elasticStore.storeTweets(tweets, user);

        return tweets.size()+" Tweets Stored";
    }

    // Getting the people (in order of their followers count) that the user follows
    public List<Document> fetchFamousFolloweesFromMongo(String user){
        return mongoService.getFamousFollowees(user);
    }

    // Returns the aggregation result of the user's followers count grouped by location
    public HashMap<String, Integer> fetchFanBaseInfoFromES(String user){
        return elasticFetch.getFanFollowingData(user);
    }

    public LinkedHashMap<String, LinkedHashMap<String, Integer>> fetchDateHistogramTweetsDatafromES(){
        return elasticFetch.getTweetDateHistogram();
    }
}
