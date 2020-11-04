package com.amberlight.blog.struct.security.auth;

import com.amberlight.blog.struct.exception.ServerException;

public final class ReCaptchaUnavailableException extends ServerException {

    public ReCaptchaUnavailableException() {
        super();
    }

    public ReCaptchaUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReCaptchaUnavailableException(String message) {
        super(message);
    }

    public ReCaptchaUnavailableException(Throwable cause) {
        super(cause);
    }

    public ReCaptchaUnavailableException(String errorMessage, Integer code) {
        super(errorMessage, code);
    }

    public ReCaptchaUnavailableException(Integer logEventId, String errorMessage, Integer code) {
        super(logEventId, errorMessage, code);
    }

    public ReCaptchaUnavailableException(Integer logEventId, String errorMessage) {
        super(logEventId, errorMessage);
    }

}
