package com.example.TwitterDataManageMongoES.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.lang.Runtime;

@Configuration
@ComponentScan(basePackages = "com.example.TwitterDataManageMongoES")
public class MongoCollectionConfig {
    private MongoCollection collection;

    @Bean
    public MongoCollection getMongoCollection(@Value("${mongo.uri}") String mongouri){
        try{
            MongoClient client = MongoClients.create(mongouri);
            collection=client.getDatabase("Twitter").getCollection("followees");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return collection;
    }
}
