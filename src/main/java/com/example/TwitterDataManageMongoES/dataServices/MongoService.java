package com.example.TwitterDataManageMongoES.dataServices;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

@Service
public class MongoService {
    MongoCollection<Document> collection=null;

    // Followees are sorted in the order of their followers count(measure of being famous)
    public List<Document> getFamousFollowees(String user){
        Bson matchStage= Aggregates.match(Filters.eq("User", user));
        Bson sortStage=Aggregates.sort(Sorts.orderBy(Sorts.descending("Followers")));

        List<Document> friends = new ArrayList<>();
        collection.aggregate(Arrays.asList(matchStage, sortStage)).forEach(document -> friends.add(document));
        return friends;
    }

    public void storeFollowees(List<TwitterProfile> friends, String user){
        ListIterator<TwitterProfile> it=friends.listIterator();
        while(it.hasNext()){
            TwitterProfile friend=it.next();

            Document doc=new Document("_id", new ObjectId())
                    .append("User", user)
                    .append("Name", friend.getName())
                    .append("UserName", friend.getScreenName())
                    .append("Description", friend.getDescription())
                    .append("Following", friend.getFriendsCount())
                    .append("Followers", friend.getFollowersCount())
                    .append("Location", friend.getLocation());


            collection.insertOne(doc);
        }
    }

    public MongoService(MongoCollection collection){
        this.collection=collection;
    }
}
