package com.springboot.dataTransferObject.repositry;

import com.springboot.dataTransferObject.model.location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<location,String> {
}
