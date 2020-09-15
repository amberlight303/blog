package com.amberlight.cloud.gateway.auth.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^((?=.{1,64}?(?=@))([\\w!#$%&'*+\\/=?_`{|}~^-]+(?:\\.[\\w!#$%&'*+\\/=?_`{|}~^-]+)*@))((?=.{4,253}$)(((?!-)(?!\\d)[a-z0-9-]{0,62}[a-z0-9]\\.)+[a-z]{2,63}$)|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\])$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return (validateEmail(username));
    }

    private boolean validateEmail(final String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}
