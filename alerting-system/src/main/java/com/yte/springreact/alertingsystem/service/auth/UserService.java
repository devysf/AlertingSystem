package com.yte.springreact.alertingsystem.service.auth;


import com.yte.springreact.alertingsystem.entity.auth.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
