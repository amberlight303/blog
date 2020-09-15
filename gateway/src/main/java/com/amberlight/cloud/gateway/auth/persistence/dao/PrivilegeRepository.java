package com.amberlight.cloud.gateway.auth.persistence.dao;


import com.amberlight.cloud.gateway.auth.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
