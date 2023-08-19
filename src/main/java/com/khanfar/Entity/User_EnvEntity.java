package com.khanfar.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "user_env_TBL")
public class User_EnvEntity extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;


    @ManyToOne
    @JoinColumn(name = "env_id")
    private EnvironmentDescription environment ;


    @Column(name = "created_date")
    private Date date ;


}
