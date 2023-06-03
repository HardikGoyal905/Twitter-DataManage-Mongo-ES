package com.example.TwitterDataManageMongoES.dataServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterFetch {
    TwitterTemplate twitter;

    public List<TwitterProfile> fetchFollowers(String user) {

        List<TwitterProfile>totalFollowers=new ArrayList<TwitterProfile>();

        CursoredList<TwitterProfile> cursoredList = twitter.friendOperations().getFollowers(user);
        while(!cursoredList.isEmpty()){
            List<TwitterProfile>followers=cursoredList;
            totalFollowers.addAll(followers);
            cursoredList = twitter.friendOperations().getFollowersInCursor(user, cursoredList.getNextCursor());
        }
        System.out.println(totalFollowers.size()+" Followers Fetched");
        return totalFollowers;
    }

    public List<TwitterProfile> fetchFollowees(String user) {

        List<TwitterProfile>totalFriends=new ArrayList<TwitterProfile>();

        CursoredList<TwitterProfile> cursoredList = twitter.friendOperations().getFriends(user);
        while(!cursoredList.isEmpty()){
            List<TwitterProfile>friends=cursoredList;
            totalFriends.addAll(friends);
            cursoredList = twitter.friendOperations().getFriendsInCursor(user, cursoredList.getNextCursor());
        }
        System.out.println(totalFriends.size()+" Followees Fetched");
        return totalFriends;
    }

    public List<Tweet> fetchTweets(String user){
        List<Tweet> tweets = twitter.timelineOperations().getUserTimeline(user, 200);
        return tweets;
    }

    @Autowired
    public TwitterFetch(TwitterTemplate twitter) {
        this.twitter=twitter;
    }
}
