package com.kimbactran.magicpostbe.controller;

import com.kimbactran.magicpostbe.dto.*;
import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.entity.enumtype.UserRole;
import com.kimbactran.magicpostbe.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/getUserByRole")
    public ResponseEntity<List<User>> getUserByRole(@RequestParam UserRole role) {
        return ResponseEntity.ok(authenticationService.getUserByRole(role));
    }

    @PostMapping("/createStaffAccount")
    public ResponseEntity<User> createStaffAccount(@RequestBody StaffUserRequest staffUserRequest) {
        return ResponseEntity.ok(authenticationService.createStaffAccount(staffUserRequest));
    }


    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        authenticationService.deleteUser(userId);
        return ResponseEntity.ok("Delete User Successfully!");
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(authenticationService.getCurrentUser());
    }
}
