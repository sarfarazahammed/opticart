package com.sarfaraz.opticart.security.exceptions.types;

import javax.security.auth.login.LoginException;

public class UserNotFoundException extends LoginException {

    private static final long serialVersionUID = 1112878680072211786L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
