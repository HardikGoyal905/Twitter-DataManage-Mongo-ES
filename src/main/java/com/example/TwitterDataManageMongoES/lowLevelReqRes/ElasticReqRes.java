package com.example.TwitterDataManageMongoES.lowLevelReqRes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ElasticReqRes {
    private static String followersurl = "http://localhost:9200/followers/_search";
    private static String tweeturl="http://localhost:9200/tweets/_search";

    public LinkedHashMap<String, LinkedHashMap<String, Integer> > tweetDateHistogram() {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = tweetDateHistogramAggregationRequest();

        CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // Process the response when it's available
        LinkedHashMap<String, LinkedHashMap<String, Integer> > result = new LinkedHashMap<>();
        responseFuture.thenAccept(response -> {
            int statusCode = response.statusCode();
            String responseBody = response.body();

            result.putAll(dateHistogramJsonParser(responseBody));
        });

        responseFuture.join();

        return result;
    }

    public TreeMap<String, LinkedHashMap<String, Integer> > dateHistogramJsonParser(String body) {
        TreeMap<String, LinkedHashMap<String, Integer> > result = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                return key2.compareTo(key1);
            }
        });

        try {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(body, JsonObject.class);

            JsonArray jsonArray = json.get("aggregations").getAsJsonObject().get("MonthlyTweets").getAsJsonObject().get("buckets").getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject date = jsonArray.get(i).getAsJsonObject();
                String key = date.get("key_as_string").getAsString();


                JsonArray userWise= date.get("users").getAsJsonObject().get("buckets").getAsJsonArray();
                LinkedHashMap<String, Integer> userWiseResult=new LinkedHashMap<>();
                for(int j=0; j<userWise.size(); j++){
                    JsonObject user = userWise.get(j).getAsJsonObject();
                    String userKey=user.get("key").getAsString();
                    int count=user.get("doc_count").getAsInt();
                    userWiseResult.put(userKey, count);
                }
                result.put(key, userWiseResult);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public static HttpRequest tweetDateHistogramAggregationRequest(){
        // Define the request body
        String requestBody = "{\n" +
                "  \"size\": 0,\n" +
                "  \"aggs\":\n" +
                "        {\n" +
                "            \"MonthlyTweets\":\n" +
                "            {\n" +
                "                \"date_histogram\": {\n" +
                "                \"field\": \"createdAt\",\n" +
                "                        \"interval\": \"month\"\n" +
                "            },\n" +
                "                \"aggs\":\n" +
                "                {\n" +
                "                    \"users\":\n" +
                "                    {\n" +
                "                        \"terms\":\n" +
                "                        {\n" +
                "                           \"field\": \"user.keyword\",\n" +
                "                           \"min_doc_count\": 0\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }";

        // Create the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tweeturl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return request;
    }



    public HashMap<String, Integer> followersLocationAggregation(String user) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = followerLocationAggregationRequest(user);

        CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // Process the response when it's available
        HashMap<String, Integer> result=new HashMap<>();
        responseFuture.thenAccept(response -> {
            int statusCode = response.statusCode();
            String responseBody = response.body();

            result.putAll(followersLocationJsonParser(responseBody));
        });

        responseFuture.join();
        return result;
    }

    public HashMap<String, Integer> followersLocationJsonParser(String body) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        try {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(body, JsonObject.class);

            JsonArray jsonArray = json.get("aggregations").getAsJsonObject().get("location").getAsJsonObject().get("buckets").getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject location = jsonArray.get(i).getAsJsonObject();
                String key = location.get("key").getAsString();
                int count = location.get("doc_count").getAsInt();
                result.put(key, count);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    
    public static HttpRequest followerLocationAggregationRequest(String user){
        // Define the request body
        String requestBody = "{\n" +
                "  \"size\": 0,\n" +
                "  \"query\":\n" +
                "  {\n" +
                "    \"bool\":\n" +
                "    {\n" +
                "      \"must\":\n" +
                "      {\n" +
                "        \"match\":\n" +
                "        {\n" +
                "          \"user\": \""+user+"\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"aggs\":\n" +
                "  {\n" +
                "    \"location\":\n" +
                "    {\n" +
                "      \"terms\":\n" +
                "      {\n" +
                "        \"field\": \"location\",\n" +
                "        \"size\": 10\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        // Create the HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(followersurl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return request;
    }
    
    // Actually I am adding this comment here, becozz I don't know what to do with it. Let me delete some file. Don't worry, its only for experimental purpose.


}
