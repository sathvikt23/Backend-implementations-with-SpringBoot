package com.example.kafka.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class consumer {
  @KafkaListener(topics="topicFromSpring",groupId="test-group")
    public void consumeMessages(String message){
      System.out.println("Recve : "+message);
  }

}
