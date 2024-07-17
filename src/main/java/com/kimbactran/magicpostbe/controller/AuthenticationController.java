package com.kimbactran.magicpostbe.controller;

import com.kimbactran.magicpostbe.dto.JwtAuthenticationResponse;
import com.kimbactran.magicpostbe.dto.RefreshTokenRequest;
import com.kimbactran.magicpostbe.dto.SignInRequest;
import com.kimbactran.magicpostbe.dto.SignUpRequest;
import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.register(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.login(signInRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
