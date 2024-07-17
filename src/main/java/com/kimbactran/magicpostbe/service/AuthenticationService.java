package com.kimbactran.magicpostbe.service;

import com.kimbactran.magicpostbe.dto.JwtAuthenticationResponse;
import com.kimbactran.magicpostbe.dto.RefreshTokenRequest;
import com.kimbactran.magicpostbe.dto.SignInRequest;
import com.kimbactran.magicpostbe.dto.SignUpRequest;
import com.kimbactran.magicpostbe.entity.User;

public interface AuthenticationService {
    User register(SignUpRequest signUpRequest);
    JwtAuthenticationResponse login(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
