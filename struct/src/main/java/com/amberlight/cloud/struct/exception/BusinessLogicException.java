package com.amberlight.cloud.struct.exception;

public class BusinessLogicException extends RuntimeException {

    private Integer logEventId;

    private Integer code;

    public BusinessLogicException() {
        super();
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(Throwable cause) {
        super(cause);
    }

    public BusinessLogicException(String errorMessage, Integer code) {
        super(errorMessage);
        this.code = code;
    }

    public BusinessLogicException(Integer logEventId, String errorMessage, Integer code) {
        super(errorMessage);
        this.logEventId = logEventId;
        this.code = code;
    }

    public BusinessLogicException(Integer logEventId, String errorMessage) {
        super(errorMessage);
        this.logEventId = logEventId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getLogEventId() {
        return logEventId;
    }

    public void setLogEventId(Integer logEventId) {
        this.logEventId = logEventId;
    }
}
