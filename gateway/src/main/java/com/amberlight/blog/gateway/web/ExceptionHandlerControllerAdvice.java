package com.amberlight.blog.gateway.web;

import com.amberlight.blog.struct.dto.ErrorDto;
import com.amberlight.blog.struct.exception.BusinessLogicException;
import com.amberlight.blog.struct.exception.ServerException;
import com.amberlight.blog.struct.log4j2.CustomMessage;
import com.amberlight.blog.struct.security.auth.*;
import com.amberlight.blog.struct.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @Autowired
    private MessageSource messages;

    private static final Logger logger = LogManager.getLogger(ExceptionHandlerControllerAdvice.class);

    private final HttpHeaders defaultHeaders = new HttpHeaders() {{
        add(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);
    }};

    // 400

    @ExceptionHandler(value = BindException.class)
    protected ResponseEntity<ErrorDto> handleBindException(BindException ex, WebRequest request) {
        logger.error(new CustomMessage(ex.getMessage()), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(defaultHeaders)
                .body(new ErrorDto("Wrong binding."));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             WebRequest request) {
        logger.error(new CustomMessage(ex.getMessage()), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(defaultHeaders)
                .body(new ErrorDto("Method argument not valid."));
    }

    @ExceptionHandler(value = InvalidOldPasswordException.class)
    protected ResponseEntity<ErrorDto> handleInvalidOldPasswordException(InvalidOldPasswordException ex,
                                                                         WebRequest request) {
        logger.error(new CustomMessage(ex.getMessage()), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(defaultHeaders)
                .body(new ErrorDto(messages.getMessage("message.invalidOldPassword", null, request.getLocale())));
    }

    @ExceptionHandler(value = ReCaptchaInvalidException.class)
    protected ResponseEntity<ErrorDto> handleReCaptchaInvalidException(ReCaptchaInvalidException ex,
                                                                         WebRequest request) {
        logger.error(new CustomMessage(ex.getMessage()), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(defaultHeaders)
                .body(new ErrorDto(messages.getMessage("message.invalidReCaptcha", null, request.getLocale())));
    }

    // 403

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(defaultHeaders)
                .body(new ErrorDto("Access denied."));
    }

    // 404

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex,
                                                                   WebRequest request) {
        logger.error(new CustomMessage(ex.getMessage()), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(defaultHeaders)
                .body(new ErrorDto(messages.getMessage("message.userNotFound", null, request.getLocale())));
    }

    // 409

    @ExceptionHandler(value = UserAlreadyExistException.class)
    protected ResponseEntity<ErrorDto> handleUserAlreadyExistException(UserAlreadyExistException ex,
                                                                       WebRequest request) {
        logger.error(new CustomMessage(ex.getMessage()), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).headers(defaultHeaders)
                .body(new ErrorDto(messages.getMessage("message.regError", null, request.getLocale())));
    }

    // 450

    @ExceptionHandler(value = BusinessLogicException.class)
    protected ResponseEntity<ErrorDto> handleBusinessLogicException(BusinessLogicException ex, WebRequest request) {
        logger.error(new CustomMessage(ex.getLogEventId(), ex.getMessage(), constructCustomFields(ex.getCode())), ex);
        return ResponseEntity.status(HttpUtil.HTTP_STATUS_BUSINESS_LOGIC_ERROR).headers(defaultHeaders)
                .body(new ErrorDto(ex.getMessage(), ex.getCode()));
    }

    // 500

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorDto> handleException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(defaultHeaders)
                .body(new ErrorDto("Oops, something went wrong."));
    }

    @ExceptionHandler(value = ServerException.class)
    protected ResponseEntity<ErrorDto> handleServerException(ServerException ex, WebRequest request) {
        logger.error(new CustomMessage(ex.getLogEventId(), ex.getMessage(), constructCustomFields(ex.getCode())), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(defaultHeaders)
                .body(new ErrorDto(ex.getMessage(), ex.getCode()));
    }

    @ExceptionHandler(value = ReCaptchaUnavailableException.class)
    protected ResponseEntity<ErrorDto> handleReCaptchaUnavailableException(ReCaptchaUnavailableException ex,
                                                                           WebRequest request) {
        logger.error(new CustomMessage(ex.getMessage()), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(defaultHeaders)
                .body(new ErrorDto(messages.getMessage("message.unavailableReCaptcha", null, request.getLocale())));
    }

    private Map<String, Object> constructCustomFields(Integer exceptionCode) {
        if (exceptionCode != null) {
            Map<String, Object> customFields;
            customFields = new HashMap<>();
            customFields.put("code", exceptionCode);
            return customFields;
        }
        return null;
    }

}
