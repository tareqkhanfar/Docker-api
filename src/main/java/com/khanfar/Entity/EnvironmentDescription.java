package com.khanfar.Entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "environment_TBL")
public class EnvironmentDescription extends PanacheEntityBase {

    @Id
    private String envID;
    private String labelName ;

    private Long cpu_core ;
    private Long memory_size ;

    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User_EnvEntity> userEnvs;

    public EnvironmentDescription() {
    }


}
