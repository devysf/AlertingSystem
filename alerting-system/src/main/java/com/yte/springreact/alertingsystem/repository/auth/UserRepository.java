package com.yte.springreact.alertingsystem.repository.auth;

import com.yte.springreact.alertingsystem.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
