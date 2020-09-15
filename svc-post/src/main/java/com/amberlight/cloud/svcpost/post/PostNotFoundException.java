package com.amberlight.cloud.svcpost.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class PostNotFoundException extends RuntimeException {
    PostNotFoundException(String message) {
        super(message);
    }
}
