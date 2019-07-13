package com.yte.springreact.alertingsystem.service.auth;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
