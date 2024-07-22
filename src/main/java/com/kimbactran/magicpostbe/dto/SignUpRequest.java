package com.kimbactran.magicpostbe.dto;

import com.kimbactran.magicpostbe.entity.UserRole;
import lombok.Data;

@Data

public class SignUpRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
    private Long postPointId;
}
