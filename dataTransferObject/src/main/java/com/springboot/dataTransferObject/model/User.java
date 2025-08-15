package com.springboot.dataTransferObject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users")
public class User {
    @Id
    public String id ;
    @Column(name="Name")
    public String name ;


    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name ="location_id")
    public location location ;

}
