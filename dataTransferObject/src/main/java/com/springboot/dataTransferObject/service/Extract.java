package com.springboot.dataTransferObject.service;


import com.springboot.dataTransferObject.dto.UserLocation;
import com.springboot.dataTransferObject.model.User;
import com.springboot.dataTransferObject.repositry.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class Extract {
    @Autowired
    UserRepo userepo;
    public UserLocation convert(User user ){
           UserLocation userdata = new UserLocation();
           userdata.setId(user.getId());
           userdata.setName(user.getName());
           userdata.setCity(user.getLocation().getCity());
           return userdata ;

    }

    public List<UserLocation> data (){
        List<User> fulldata =userepo.findAll();
        List<UserLocation> finaldata =new ArrayList<>();
        for (User i : fulldata){
            finaldata.add(this.convert(i));
        }
        return finaldata;
    }


}
