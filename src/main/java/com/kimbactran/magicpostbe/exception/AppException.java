package com.kimbactran.magicpostbe.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String code;
    private String message;
    private Object data;

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return getCode() + ": " + getMessage();
    }

    public AppException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppException(Exception e, String message) {
        super(e);
        this.message = message;
    }

    public AppException(Exception e, String code, String message) {
        super(e);
        this.code = code;
        this.message = message;
    }

    public AppException(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public AppException(String message) {
        super(message);
        this.message = message;
    }

    public static AppException badRequest(String message) {
        return AppException.builder().code(String.valueOf(HttpStatus.BAD_REQUEST)).message(message).build();
    }

    public static AppException dataExists(String message) {
        return AppException.builder().code(String.valueOf(HttpStatus.CONFLICT)).message(message).build();
    }

    public static AppException unAuthorized() {
        return AppException.builder().code(String.valueOf(HttpStatus.UNAUTHORIZED)).message("Not authorized").build();
    }

    public static AppException forbidden() {
        return AppException.builder().code(String.valueOf(HttpStatus.FORBIDDEN)).message("Forbidden").build();
    }

    public static AppException notFound(String message) {
        return AppException.builder().code(String.valueOf(HttpStatus.NOT_FOUND)).message(message).build();
    }

    public static AppException noHaveRole(String username){
        String message = String.format("User %s has no role", username);
        return AppException.builder().code(String.valueOf(HttpStatus.UNAUTHORIZED)).message(message).build();
    }
}
