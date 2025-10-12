package com.example.kafka.controller;

import com.example.kafka.dto.messagerequest;
import com.example.kafka.producer.producerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafkaService")
public class kafkaserver {
    @Autowired
    producerservice producer ;
    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody messagerequest req){
        try{
            producer.sendMessage(req.getTopic() ,req.getMessage());
            return ResponseEntity.status(200).body("Sent!");
        }catch(Exception e){
            return ResponseEntity.status(500).body("Govinda !"+e);
        }

    }

}
