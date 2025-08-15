package com.springboot.dataTransferObject.controller;


import com.springboot.dataTransferObject.dto.UserLocation;
import com.springboot.dataTransferObject.service.Extract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
public class rest {
    @Autowired
    Extract extract ;
    @GetMapping("/")
    public ResponseEntity<List<UserLocation>> check(){
        List<UserLocation> data =extract.data();
        return ResponseEntity.status(200).body(data);

    }

}
