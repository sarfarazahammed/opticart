package com.sarfaraz.opticart.security.exceptions.types;

import javax.security.auth.login.LoginException;

public class UserAlreadyExistsException extends LoginException {

    private static final long serialVersionUID = 1112878680072211787L;

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
