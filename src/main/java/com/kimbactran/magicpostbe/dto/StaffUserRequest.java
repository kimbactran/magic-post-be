package com.kimbactran.magicpostbe.dto;

import com.kimbactran.magicpostbe.entity.UserRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StaffUserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
    private Long postPointId;
}
