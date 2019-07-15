package com.yte.springreact.alertingsystem.validator;


public class ValidationError {
    private String usernameError;
    private String passwordError;
    private String passwordConfirmError;

    public ValidationError(){

    }

    public ValidationError(String usernameError, String passwordError, String passwordConfirmError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.passwordConfirmError = passwordConfirmError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPasswordConfirmError() {
        return passwordConfirmError;
    }

    public void setPasswordConfirmError(String passwordConfirmError) {
        this.passwordConfirmError = passwordConfirmError;
    }
}
