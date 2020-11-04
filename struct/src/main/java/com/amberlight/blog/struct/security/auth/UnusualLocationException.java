package com.amberlight.blog.struct.security.auth;

import com.amberlight.blog.struct.exception.BusinessLogicException;
import org.springframework.security.core.AuthenticationException;

public final class UnusualLocationException extends BusinessLogicException {

    public UnusualLocationException() {
        super();
    }

    public UnusualLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnusualLocationException(String message) {
        super(message);
    }

    public UnusualLocationException(Throwable cause) {
        super(cause);
    }

    public UnusualLocationException(String errorMessage, Integer code) {
        super(errorMessage, code);
    }

    public UnusualLocationException(Integer logEventId, String errorMessage, Integer code) {
        super(logEventId, errorMessage, code);
    }

    public UnusualLocationException(Integer logEventId, String errorMessage) {
        super(logEventId, errorMessage);
    }

}
