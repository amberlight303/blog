package com.amberlight.blog.gateway.auth.persistence.dao;


import com.amberlight.blog.struct.security.UserLocation;
import com.amberlight.blog.struct.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    UserLocation findByCountryAndUser(String country, User user);

}
