package com.khanfar.Repository;

import com.khanfar.Entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {


    @Transactional
    public User findByUsernameAndPassword(String username, String password) {
        return find("username = ?1 and password = ?2", username, password).firstResult();
    }


}
