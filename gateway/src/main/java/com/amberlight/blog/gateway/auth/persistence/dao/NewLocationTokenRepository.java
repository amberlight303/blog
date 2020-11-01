package com.amberlight.blog.gateway.auth.persistence.dao;



import com.amberlight.blog.struct.security.UserLocation;
import com.amberlight.blog.struct.security.NewLocationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(UserLocation userLocation);

}
