package com.khanfar.Service;


import com.khanfar.DTO.UserDTO;
import com.khanfar.Entity.EnvironmentDescription;
import com.khanfar.Entity.User;
import com.khanfar.Controller.Repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;


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
            public List<EnvironmentDescription> findContainerNameByUsername(String username) {
                System.out.println("user name : " + username);
                System.out.println("LIST "+user.findContainerNameByUsername(username));
                        return user.findContainerNameByUsername(username) ;
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
