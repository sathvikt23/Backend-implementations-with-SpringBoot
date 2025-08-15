package com.springboot.dataTransferObject;

import com.springboot.dataTransferObject.model.User;
import com.springboot.dataTransferObject.model.location;
import com.springboot.dataTransferObject.repositry.LocationRepo;
import com.springboot.dataTransferObject.repositry.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataTransferObjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DataTransferObjectApplication.class, args);
	}

	@Autowired
	public LocationRepo loc;

	@Autowired
	public UserRepo ur;

    @Override
	public void run (String args []) throws Exception {
           location loct = new location();
		   loct.setId("1");
		   loct.setCity("Hyderabad");
		   loc.save(loct);

		   User us = new User();
		   us.setName("Sathvik");
		   us.setId("1");
		   us.setLocation(loct);
		   ur.save(us);

	}


}
