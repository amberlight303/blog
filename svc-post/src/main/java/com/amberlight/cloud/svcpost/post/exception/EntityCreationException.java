package com.amberlight.cloud.svcpost.post.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntityCreationException extends RuntimeException {
    public EntityCreationException(String message) {
        super(message);
    }
}
