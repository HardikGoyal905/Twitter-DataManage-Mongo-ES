package com.example.TwitterDataManageMongoES.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.io.*;
import java.util.ArrayList;

@Configuration
@ComponentScan(basePackages = "com.example.TwitterDataManageMongoES")
public class TwitterTemplateConfig {
    private ArrayList<String> cred;
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;


    private void readCred() {
        try {
            FileReader fileReader = new FileReader(new File("src/main/resources/Cred.txt"));
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                cred.add(line);
            }
        } catch (Exception fileNotFoundOrIO) {
            System.out.println(fileNotFoundOrIO.getMessage());
        }


        consumerKey = cred.get(0);
        consumerSecret = cred.get(1);
        accessToken = cred.get(2);
        accessTokenSecret = cred.get(3);
    }

    @Primary
    @Bean
    public TwitterTemplate getTwitterTemplate(){
        cred=new ArrayList<String>();
        readCred();
        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }

}
