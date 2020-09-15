package com.amberlight.cloud.gateway.auth.persistence.dao;



import com.amberlight.cloud.gateway.auth.persistence.model.UserLocation;
import com.amberlight.cloud.gateway.auth.persistence.model.NewLocationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(UserLocation userLocation);

}
