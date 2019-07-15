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
            return;
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "This field is required");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Please use between 6 and 32 characters");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "This field is required");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Try one with at least 8 characters");
        }

        User foundUser = userService.findByUsername(user.getUsername());
        if (foundUser== null ) {
            errors.rejectValue("username", "Username or Password are incorrect.");
        }

    }
}
