package com.example.TwitterDataManageMongoES.services;

import com.example.TwitterDataManageMongoES.dataServices.ElasticFetch;
import com.example.TwitterDataManageMongoES.dataServices.ElasticStore;
import com.example.TwitterDataManageMongoES.dataServices.MongoService;
import com.example.TwitterDataManageMongoES.dataServices.TwitterFetch;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MainServiceTest {
    @Autowired
    MainService service;

    @Mock
    TwitterFetch twitterFetch;

    @Mock
    ElasticFetch elasticFetch;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void storeTwitterTweetsIntoESTest(){
        Tweet tweet=null;
        List<Tweet> tweets=new ArrayList<>();
        tweets.add(tweet);
        tweets.add(tweet);

        when(twitterFetch.fetchTweets("imVkohli")).thenReturn(tweets);

        String result=service.storeTwitterTweetsIntoES("imVkohli");

        assertEquals(result, "2 Tweets Stored");
    }

    @Test
    public void fetchDateHistogramTweetsDatafromESTest(){
        LinkedHashMap<String, LinkedHashMap<String, Integer>> map=new LinkedHashMap<>();

        when(elasticFetch.getTweetDateHistogram()).thenReturn(map);
        LinkedHashMap<String, LinkedHashMap<String, Integer>> result=service.fetchDateHistogramTweetsDatafromES();

        assertEquals(map, result);
    }

}
