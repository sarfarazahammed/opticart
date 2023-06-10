package com.sarfaraz.opticart.commons.validator;

public class RegexValidator {

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?!.+[^A-Za-z0-9 \\\\!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]).{8,128}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]{1,64}@[a-zA-Z0-9.-]{1,128}\\.[a-zA-Z]{1,7}$";

    private RegexValidator() {
        throw new IllegalStateException("Constants Validator class");
    }

    public static boolean validPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
