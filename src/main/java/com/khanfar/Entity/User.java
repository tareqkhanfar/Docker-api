package com.khanfar.Entity;


import jakarta.persistence.*;
import lombok.Data;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.util.Set;

@Data
@Entity
@Table(name = "USER_TBL")

public class User extends PanacheEntity {
    @Column(name = "user_name")
    private String username ;
    @Column(name = "email")
    private String email ;
    @Column(name = "phone_number")
    private String phoneNumber ;

    @Column(name = "password")
    private String password ;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<User_EnvEntity> user_envEntities ;



}
