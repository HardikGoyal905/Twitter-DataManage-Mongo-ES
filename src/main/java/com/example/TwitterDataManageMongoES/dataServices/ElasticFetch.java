package com.example.TwitterDataManageMongoES.dataServices;

import com.example.TwitterDataManageMongoES.lowLevelReqRes.ElasticReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.http.client.ClientHttpRequest;

@Service
public class ElasticFetch {
    ClientHttpRequest h;
    private ElasticReqRes elasticReqRes;


    public HashMap<String, Integer> getFanFollowingData(String user){
        return elasticReqRes.followersLocationAggregation(user);
    }

    public LinkedHashMap<String, LinkedHashMap<String, Integer>> getTweetDateHistogram(){
        return elasticReqRes.tweetDateHistogram();
    }

    @Autowired
    public ElasticFetch(ElasticReqRes elasticReqRes){
        this.elasticReqRes=elasticReqRes;
    }
    
    // I am adding this here, bcoz I want to
    
    for(int i=0; i<103421){
        fdasfjk
}
