package com.amberlight.blog.struct.security.auth;

import com.amberlight.blog.struct.exception.BusinessLogicException;

public final class InvalidOldPasswordException extends BusinessLogicException {

    public InvalidOldPasswordException() {
        super();
    }

    public InvalidOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOldPasswordException(String message) {
        super(message);
    }

    public InvalidOldPasswordException(Throwable cause) {
        super(cause);
    }

    public InvalidOldPasswordException(String errorMessage, Integer code) {
        super(errorMessage, code);
    }

    public InvalidOldPasswordException(Integer logEventId, String errorMessage, Integer code) {
        super(logEventId, errorMessage, code);
    }

    public InvalidOldPasswordException(Integer logEventId, String errorMessage) {
        super(logEventId, errorMessage);
    }

}
