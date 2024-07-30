package com.kimbactran.magicpostbe.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor()
@JsonView
public class Response {
    protected String code;
    protected String message;
    protected String status;
    protected String total;
    protected Object data;

    public static Response of (String code, String message, String status, Object data) {
        return of(code, message, status, data);
    }


}
