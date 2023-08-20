package com.khanfar.Controller.Repository;

import com.khanfar.Entity.EnvironmentDescription;
import com.khanfar.Entity.User_EnvEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
public class EnvironmentRepository  implements PanacheRepository<EnvironmentDescription> {

    @PersistenceContext
    EntityManager entityManager ;

    @Transactional
    public EnvironmentDescription fetchEnvironmentByName(String envName){
        return find("labelName = ?1" , envName).firstResult();
    }


}
