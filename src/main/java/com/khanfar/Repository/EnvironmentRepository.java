package com.khanfar.Repository;

import com.khanfar.Entity.EnvironmentDescription;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class EnvironmentRepository  implements PanacheRepository<EnvironmentDescription> {


    @Transactional
    public EnvironmentDescription fetchEnvironmentByName(String envName){
        return find("containerName = ?1" , envName).firstResult();
    }
}
