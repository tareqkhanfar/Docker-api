package com.khanfar.Entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "environment_TBL")
public class EnvironmentDescription extends PanacheEntity {

    private String clientName ;
    private String containerName;

    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User_EnvEntity> userEnvs;

    public EnvironmentDescription() {
    }


    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }



    public EnvironmentDescription(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
