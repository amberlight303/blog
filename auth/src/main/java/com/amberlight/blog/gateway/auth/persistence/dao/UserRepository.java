package com.amberlight.blog.gateway.auth.persistence.dao;


import com.amberlight.blog.struct.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Override
    void delete(User user);

}
