package com.khanfar.Service;


import com.khanfar.DTO.User_EnvDTO;
import com.khanfar.Entity.EnvironmentDescription;
import com.khanfar.Entity.User;
import com.khanfar.Entity.User_EnvEntity;
import com.khanfar.Repository.ReservationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Date;

@ApplicationScoped
public class ReservationService {

    @Inject
    ReservationRepository reservationRepository ;

    @Inject
    EntityManager entityManager ;


    @Transactional
    public User_EnvDTO newReserve(User_EnvDTO user_envDTO) {
        User_EnvEntity user_envEntity = convertToEntity(user_envDTO);

        reservationRepository.persist(user_envEntity);
        return user_envDTO;
    }

    private User_EnvEntity convertToEntity(User_EnvDTO user_envDTO) {
        User_EnvEntity user_envEntity = new User_EnvEntity();

      User user1 =  entityManager.find(User.class , user_envDTO.getUser_id());
        user_envEntity.setUser(user1);

        EnvironmentDescription environmentDescription = entityManager.find(EnvironmentDescription.class , user_envDTO.getEnv_id());

        user_envEntity.setEnvironment(environmentDescription);
        user_envEntity.setDate(new Date());

        return user_envEntity;
    }
}
