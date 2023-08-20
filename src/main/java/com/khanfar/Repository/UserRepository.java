package com.khanfar.Repository;

import com.khanfar.Entity.EnvironmentDescription;
import com.khanfar.Entity.User;
import com.khanfar.Entity.User_EnvEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;


@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public User findByUserName(String username) {
        return find("username = ?1", username).firstResult();
    }

    @Transactional
    public User findByUsernameAndPassword(String username, String password) {
        return find("username = ?1 and password = ?2", username, password).firstResult();
    }

    @Transactional
    public List<EnvironmentDescription> findContainerNameByUsername(String username) {

        System.out.println("from repository");


        String sql = "SELECT e.containerName " +
                "FROM ENVIRONMENT_TBL e " +
                "JOIN user_env_TBL ue ON e.id = ue.env_id " +
                "JOIN user_tbl u ON u.id = ue.user_id AND u.user_name = :username" ;

        return entityManager.createNativeQuery(sql).setParameter("username", username).getResultList();

    }
}
