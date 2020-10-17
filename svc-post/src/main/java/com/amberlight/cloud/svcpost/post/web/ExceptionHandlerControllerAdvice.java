package com.amberlight.cloud.svcpost.post.web;

import com.amberlight.cloud.struct.util.HttpUtil;
import com.amberlight.cloud.struct.exception.BusinessLogicException;
import com.amberlight.cloud.struct.exception.ServerException;
import com.amberlight.cloud.struct.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private final HttpHeaders defaultHeaders = new HttpHeaders() {{
        add(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);
    }};

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorDto> handleException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(defaultHeaders)
                .body(new ErrorDto("Oops, something went wrong."));
    }

    @ExceptionHandler(value = BusinessLogicException.class)
    protected ResponseEntity<ErrorDto> handleBusinessLogicException(BusinessLogicException ex, WebRequest request) {
        return ResponseEntity.status(HttpUtil.HTTP_STATUS_BUSINESS_LOGIC_ERROR).headers(defaultHeaders)
                .body(new ErrorDto(ex.getMessage(), ex.getCode()));
    }

    @ExceptionHandler(value = ServerException.class)
    protected ResponseEntity<ErrorDto> handleServerException(ServerException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(defaultHeaders)
                .body(new ErrorDto(ex.getMessage(), ex.getCode()));
    }

}
