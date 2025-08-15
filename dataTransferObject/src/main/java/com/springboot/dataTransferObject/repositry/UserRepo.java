package com.springboot.dataTransferObject.repositry;

import com.springboot.dataTransferObject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
}
