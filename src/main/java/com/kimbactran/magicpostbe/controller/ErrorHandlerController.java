package com.kimbactran.magicpostbe.controller;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Xử lý lỗi và trả về phản hồi phù hợp
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
