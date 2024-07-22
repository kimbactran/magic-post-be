package com.kimbactran.magicpostbe.service.serviceimpl;

import com.kimbactran.magicpostbe.dto.*;
import com.kimbactran.magicpostbe.entity.PostPoint;
import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.entity.UserRole;
import com.kimbactran.magicpostbe.exception.AppException;
import com.kimbactran.magicpostbe.repository.PostPointRepository;
import com.kimbactran.magicpostbe.repository.UserRepository;
import com.kimbactran.magicpostbe.service.AuthenticationService;
import com.kimbactran.magicpostbe.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PostPointRepository postPointRepository;


    public User register(SignUpRequest signUpRequest) {
        User user = new User();
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AppException("Email already exists");
        }
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(signUpRequest.getRole());
        user.setPhone(signUpRequest.getPhone());
        user.setUsername(signUpRequest.getFirstName() + signUpRequest.getLastName());
        PostPoint postPoint = postPointRepository.findById(signUpRequest.getPostPointId()).orElseThrow(() -> AppException.notFound("Post Point not found"));
        user.setPostPointId(signUpRequest.getPostPointId());
        User savedUser = userRepository.save(user);
        postPoint.setPointLeaderId(savedUser.getId());
        return savedUser;
    }

    public User createStaffAccount(StaffUserRequest staffUserRequest) {
        User user = new User();
        if(userRepository.existsByEmail(staffUserRequest.getEmail())) {
            throw new AppException("Email already exists");
        }
        user.setFirstName(staffUserRequest.getFirstName());
        user.setLastName(staffUserRequest.getLastName());
        user.setEmail(staffUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(staffUserRequest.getPassword()));
        user.setRole(staffUserRequest.getRole());
        user.setPhone(staffUserRequest.getPhone());
        user.setUsername(staffUserRequest.getFirstName() + staffUserRequest.getLastName());
        PostPoint postPoint = postPointRepository.findById(staffUserRequest.getPostPointId()).orElseThrow(() -> AppException.notFound("Post Point not found"));
        user.setPostPointId(staffUserRequest.getPostPointId());
        user.setRole(staffUserRequest.getRole());
        User savedUser = userRepository.save(user);
        postPoint.setPointLeaderId(savedUser.getId());
        return savedUser;
    }

    public JwtAuthenticationResponse login(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new RuntimeException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractEmail(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("Invalid token"));
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        } else {
            throw AppException.badRequest("Token invalid");
        }
    }

    public List<User> getUserByRole(UserRole userRole){
        return userRepository.findByRole(userRole);
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not found"));
        userRepository.deleteById(userId);
    }

}
