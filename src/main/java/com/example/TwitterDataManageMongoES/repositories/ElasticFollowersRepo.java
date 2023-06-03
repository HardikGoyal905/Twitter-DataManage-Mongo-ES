package com.example.TwitterDataManageMongoES.repositories;

import com.example.TwitterDataManageMongoES.models.Follower;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticFollowersRepo extends ElasticsearchRepository<Follower, String> {
}
