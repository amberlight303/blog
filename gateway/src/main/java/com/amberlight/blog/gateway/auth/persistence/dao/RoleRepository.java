package com.amberlight.blog.gateway.auth.persistence.dao;


import com.amberlight.blog.struct.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
