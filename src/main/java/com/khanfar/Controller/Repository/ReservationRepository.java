package com.khanfar.Controller.Repository;


import com.khanfar.Entity.User_EnvEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ReservationRepository implements PanacheRepository<User_EnvEntity> {

    @PersistenceContext
    EntityManager entityManager ;



}
