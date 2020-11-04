package com.amberlight.blog.struct.security.auth;

import com.amberlight.blog.struct.exception.BusinessLogicException;

public final class UserAlreadyExistException extends BusinessLogicException {

    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExistException(String errorMessage, Integer code) {
        super(errorMessage, code);
    }

    public UserAlreadyExistException(Integer logEventId, String errorMessage, Integer code) {
        super(logEventId, errorMessage, code);
    }

    public UserAlreadyExistException(Integer logEventId, String errorMessage) {
        super(logEventId, errorMessage);
    }

}
