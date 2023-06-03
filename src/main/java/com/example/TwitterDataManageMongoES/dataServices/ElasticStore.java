package com.example.TwitterDataManageMongoES.dataServices;

import com.example.TwitterDataManageMongoES.repositories.ElasticFollowersRepo;
import com.example.TwitterDataManageMongoES.repositories.ElasticTweetyRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.List;
import java.util.ListIterator;

import com.example.TwitterDataManageMongoES.models.*;
import org.springframework.stereotype.Service;

@Service
public class ElasticStore {
    @Autowired
    ElasticFollowersRepo elasticFollowersRepo;

    @Autowired
    ElasticTweetyRepo tweetyRepo;

    @Autowired
    ElasticsearchOperations searchOp;

    public void storeFollowers(List<TwitterProfile> followers, String user) {

        ListIterator<TwitterProfile> it = followers.listIterator();
        while (it.hasNext()) {
            TwitterProfile follower = it.next();

            Follower elasticfriend = new Follower();
            elasticfriend.setId(new ObjectId().toString());
            elasticfriend.setDescription(follower.getDescription());
            elasticfriend.setUser(user);
            elasticfriend.setFollowers(follower.getFollowersCount());
            elasticfriend.setFollowing(follower.getFriendsCount());
            elasticfriend.setLocation(follower.getLocation());
            elasticfriend.setScreenName(follower.getScreenName());

            elasticFollowersRepo.save(elasticfriend);
        }
    }

    public void storeTweets(List<Tweet> tweets, String user) {

        ListIterator<Tweet> it = tweets.listIterator();
        while (it.hasNext()) {
            Tweet tweet = it.next();

            Tweetie tweetie=new Tweetie();
            tweetie.setId(new ObjectId().toString());
            tweetie.setText(tweet.getText());
            tweetie.setFavoriteCount(tweet.getFavoriteCount());
            tweetie.setRetweetCount(tweet.getRetweetCount());
            tweetie.setUser(user);
            tweetie.setCreatedAt(tweet.getCreatedAt());


            tweetyRepo.save(tweetie);
        }
    }
}
