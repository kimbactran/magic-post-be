package com.kimbactran.magicpostbe.service;

import com.kimbactran.magicpostbe.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface JWTService {
    String generateToken(User userDetails);
    String extractEmail(String token);
    boolean isTokenValid(String token, User userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, User userDetails);
}
