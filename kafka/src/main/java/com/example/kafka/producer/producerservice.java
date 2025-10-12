package com.example.kafka.producer;


import com.example.kafka.config.kafkaConfig;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
@Service
class topicChecker{
    @Autowired
    public kafkaConfig config ;
    public  boolean verify(String topicName){

        Map<String ,Object> properties = config.producerConfig();
        try(AdminClient admin = AdminClient.create(properties)){
            ListTopicsResult topic = admin.listTopics();
            Set<String> topicNames = topic.names().get();
            return topicNames.contains(topicName);

        }catch(InterruptedException | ExecutionException e ){
            e.printStackTrace();
            return false ;
        }

    }

}
@Service
public class producerservice {
    String defaultTopicName="topicFromSpring";
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate ;
    @Autowired
    topicChecker checker;
    public void sendMessage(String message){
        kafkaTemplate.send(defaultTopicName,message);
    }
    public Boolean sendMessage(String topicName ,String message){

        if (!checker.verify(topicName)) return false ;
        kafkaTemplate.send(topicName,message);
        return true ;
    }



}
