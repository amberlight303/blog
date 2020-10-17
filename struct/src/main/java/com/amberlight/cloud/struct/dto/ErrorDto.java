package com.amberlight.cloud.struct.dto;

public class ErrorDto {

    private String error;
    private Integer code;

    public ErrorDto(String error) {
        this.error = error;
    }

    public ErrorDto(String error, Integer code) {
        this.error = error;
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
