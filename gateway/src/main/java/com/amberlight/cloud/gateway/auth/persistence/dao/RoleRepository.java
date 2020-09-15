package com.amberlight.cloud.gateway.auth.persistence.dao;


import com.amberlight.cloud.gateway.auth.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
