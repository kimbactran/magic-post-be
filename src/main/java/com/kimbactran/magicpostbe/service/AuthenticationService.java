package com.kimbactran.magicpostbe.service;

import com.kimbactran.magicpostbe.dto.*;
import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.entity.enumtype.UserRole;

import java.util.List;

public interface AuthenticationService {
    User register(SignUpRequest signUpRequest);
    User createStaffAccount(StaffUserRequest staffUserRequest);
    List<User> getUserByRole(UserRole userRole);
    void deleteUser(Long userId);
    JwtAuthenticationResponse login(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
