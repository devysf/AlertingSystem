package com.yte.springreact.alertingsystem.rest;


import com.yte.springreact.alertingsystem.entity.auth.User;
import com.yte.springreact.alertingsystem.service.auth.SecurityService;
import com.yte.springreact.alertingsystem.service.auth.UserService;
import com.yte.springreact.alertingsystem.validator.LoginValidator;
import com.yte.springreact.alertingsystem.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private LoginValidator loginValidator;


    @PostMapping("/register")
    public List<ObjectError> register(@RequestBody User userForm, BindingResult bindingResult) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }


        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return bindingResult.getAllErrors();
    }


    @PostMapping("/login")
    public  List<ObjectError> login(@RequestBody User userForm, BindingResult bindingResult) {

        System.out.println("User form  : " + userForm.getUsername() + "---" + userForm.getPassword());
        loginValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }

        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

        return  bindingResult.getAllErrors();
    }

    @GetMapping("/auth")
    public  String auth() {
           return  securityService.findLoggedInUsername();
    }

}
