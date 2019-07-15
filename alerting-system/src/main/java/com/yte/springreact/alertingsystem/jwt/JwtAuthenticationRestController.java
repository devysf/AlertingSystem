package com.yte.springreact.alertingsystem.jwt;

import com.yte.springreact.alertingsystem.entity.auth.User;
import com.yte.springreact.alertingsystem.service.auth.UserService;
import com.yte.springreact.alertingsystem.validator.LoginValidator;
import com.yte.springreact.alertingsystem.validator.UserValidator;
import com.yte.springreact.alertingsystem.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@CrossOrigin(origins={ "http://localhost:3000", "http://localhost:4200" })
public class JwtAuthenticationRestController {

  @Value("Authorization")
  private String tokenHeader;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  UserValidator userValidator;
  @Autowired
  LoginValidator loginValidator;

  @Autowired
  UserService userService;



  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> register(@RequestBody User user, BindingResult bindingResult)
          throws AuthenticationException {

    userValidator.validate(user, bindingResult);

    if (bindingResult.hasErrors()) {
      ValidationError validationError = new ValidationError();

      for(FieldError er: bindingResult.getFieldErrors()){
        System.out.println(er.getField() +" " + er.getCode());
        if(er.getField().equals("username"))
          validationError.setUsernameError(er.getCode());
        else if(er.getField().equals("password"))
          validationError.setPasswordError(er.getCode());
        else if(er.getField().equals("passwordConfirm"))
          validationError.setPasswordConfirmError(er.getCode());
      }
      return ResponseEntity.ok(validationError);
    }

    userService.save(user);

    return ResponseEntity.ok("success");
  }


  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody User user,BindingResult bindingResult)
      throws AuthenticationException {

    loginValidator.validate(user, bindingResult);

    if (bindingResult.hasErrors()) {
      ValidationError validationError = new ValidationError();

      for(FieldError er: bindingResult.getFieldErrors()){
        System.out.println(er.getField() +" " + er.getCode());
        if(er.getField().equals("username"))
          validationError.setUsernameError(er.getCode());
        else if(er.getField().equals("password"))
          validationError.setPasswordError(er.getCode());
      }
      return ResponseEntity.ok(validationError);
    }

    authenticate(user.getUsername(), user.getPassword());

    final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(token);
  }

  @ExceptionHandler({ AuthenticationException.class })
  public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
  }

  private void authenticate(String username, String password) {
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new AuthenticationException("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new AuthenticationException("INVALID_CREDENTIALS", e);
    }
  }
}

