package com.amberlight.cloud.gateway.auth.persistence.dao;


import com.amberlight.cloud.struct.security.UserLocation;
import com.amberlight.cloud.struct.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    UserLocation findByCountryAndUser(String country, User user);

}
