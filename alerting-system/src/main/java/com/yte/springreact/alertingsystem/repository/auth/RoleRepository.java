package com.yte.springreact.alertingsystem.repository.auth;

import com.yte.springreact.alertingsystem.entity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
}