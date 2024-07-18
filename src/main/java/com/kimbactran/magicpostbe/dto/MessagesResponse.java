package com.kimbactran.magicpostbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kimbactran.magicpostbe.config.ErrorCode;
import com.kimbactran.magicpostbe.exception.AppException;
import com.nimbusds.jwt.util.DateUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagesResponse<T> {
    public interface RESPONSE_STATUS {
        String SUCCESS = "SUCCESS";
        String ERROR = "ERROR";
        String ERROR_WITH_PARAMS = "ERROR_WITH_PARAMS";
    }
    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] paramCode;
    private String message;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String timestamp;
    private Long elapsedTimeMs;
    private Integer total;
    private T data;
    public MessagesResponse() {
        this.timestamp = LocalDateTime.now().toString();
        this.code = ErrorCode.SUCCESS;
        this.status = RESPONSE_STATUS.SUCCESS;
    }

    public MessagesResponse<T> error(Exception e) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = RESPONSE_STATUS.ERROR;
        if(e instanceof AppException) {
            this.code = ((AppException) e).getCode();
            this.message = ((AppException) e).getMessage();
            this.status = RESPONSE_STATUS.ERROR;
        } else {
            this.code = ErrorCode.SYSTEM_ERROR;
            this.message = e.getMessage();
        }
        return this;
    }
}
