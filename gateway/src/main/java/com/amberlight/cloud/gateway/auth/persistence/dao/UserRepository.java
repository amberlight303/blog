package com.amberlight.cloud.gateway.auth.persistence.dao;


import com.amberlight.cloud.gateway.auth.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Override
    void delete(User user);

}
