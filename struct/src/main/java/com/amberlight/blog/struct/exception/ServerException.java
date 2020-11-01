package com.amberlight.blog.struct.exception;

public class ServerException extends RuntimeException {

    private Integer logEventId;

    private Integer code;

    public ServerException() {
        super();
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String errorMessage, Integer code) {
        super(errorMessage);
        this.code = code;
    }

    public ServerException(Integer logEventId, String errorMessage, Integer code) {
        super(errorMessage);
        this.logEventId = logEventId;
        this.code = code;
    }

    public ServerException(Integer logEventId, String errorMessage) {
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
