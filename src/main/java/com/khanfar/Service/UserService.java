package com.khanfar.Service;


import com.khanfar.DTO.UserDTO;
import com.khanfar.Entity.User;
import com.khanfar.Entity.User_EnvEntity;
import com.khanfar.Repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Set;


@ApplicationScoped
public class UserService {

    @Inject
    UserRepository user ;


    @Transactional
    public UserDTO register(UserDTO userDTO){
        User user1 = convertToEntity(userDTO) ;
        user.persist(user1);
        return userDTO;

    }
    @Transactional
    public UserDTO login(UserDTO userDTO){
        User user1 = user.findByUsernameAndPassword(userDTO.getUserName() , userDTO.getPassword()) ;
        if (user1 != null) {
            return userDTO;

        }
        else {
            throw new NotFoundException("user not found ");
        }
    }




            @Transactional
            public Set<User_EnvEntity> findContainerNameByUsername(String username) {


                String jpql = "SELECT e.environment.containerName FROM User u " +
                        "JOIN u.user_envEntities ue " +
                        "JOIN ue.environment e " +
                        "WHERE u.username = :username";

     return null ;


        }





    private User convertToEntity (UserDTO userDTO ) {
        User user1 = new User() ;
        user1.setUsername(userDTO.getUserName());
        user1.setPassword(userDTO.getPassword());
        user1.setEmail(userDTO.getEmail());
        user1.setPhoneNumber(userDTO.getPhoneNumber());

        return user1;
    }
}
