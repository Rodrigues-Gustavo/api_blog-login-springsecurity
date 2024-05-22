package com.techgusta.springsecuritylogin.repositories;

import com.techgusta.springsecuritylogin.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
