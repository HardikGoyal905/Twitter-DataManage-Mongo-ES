package com.example.TwitterDataManageMongoES.repositories;

import com.example.TwitterDataManageMongoES.models.Tweetie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticTweetyRepo extends ElasticsearchRepository<Tweetie, String> {
}
