package com.amberlight.blog.struct.security.auth;

import com.amberlight.blog.struct.exception.BusinessLogicException;

public final class ReCaptchaInvalidException extends BusinessLogicException {

    public ReCaptchaInvalidException() {
        super();
    }

    public ReCaptchaInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReCaptchaInvalidException(String message) {
        super(message);
    }

    public ReCaptchaInvalidException(Throwable cause) {
        super(cause);
    }

    public ReCaptchaInvalidException(String errorMessage, Integer code) {
        super(errorMessage, code);
    }

    public ReCaptchaInvalidException(Integer logEventId, String errorMessage, Integer code) {
        super(logEventId, errorMessage, code);
    }

    public ReCaptchaInvalidException(Integer logEventId, String errorMessage) {
        super(logEventId, errorMessage);
    }

}
