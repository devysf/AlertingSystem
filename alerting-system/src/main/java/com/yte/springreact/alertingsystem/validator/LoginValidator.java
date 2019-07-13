package com.yte.springreact.alertingsystem.validator;


import com.yte.springreact.alertingsystem.entity.auth.User;
import com.yte.springreact.alertingsystem.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) throws NullPointerException {
        User user = (User) o;

        if(user.getUsername()==null || user.getPassword()==null){
            errors.rejectValue("username", "Every field is required1");
        }

    }
}
