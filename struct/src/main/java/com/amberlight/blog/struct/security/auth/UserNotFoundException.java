package com.amberlight.blog.struct.security.auth;

import com.amberlight.blog.struct.exception.BusinessLogicException;

public final class UserNotFoundException extends BusinessLogicException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final Throwable cause) {
        super(cause);
    }

}
