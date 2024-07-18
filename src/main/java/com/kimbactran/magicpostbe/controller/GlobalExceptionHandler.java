package com.kimbactran.magicpostbe.controller;

import com.kimbactran.magicpostbe.dto.MessagesResponse;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    protected static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;
    @ExceptionHandler( {Exception.class})
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        MessagesResponse msg = new MessagesResponse();
        msg.error(ex);
        LOGGER.error(msg.toString(), ex);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
