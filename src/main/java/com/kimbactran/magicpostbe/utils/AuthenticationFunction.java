package com.kimbactran.magicpostbe.utils;

import com.kimbactran.magicpostbe.dto.SignUpRequest;
import com.kimbactran.magicpostbe.entity.PostPoint;
import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.exception.AppException;
import com.kimbactran.magicpostbe.repository.PostPointRepository;
import com.kimbactran.magicpostbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFunction {
    private final UserRepository userRepository;
    private final PostPointRepository postPointRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignUpRequest signUpRequest) {
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
        PostPoint postPoint = postPointRepository.findById(signUpRequest.getPostPointId()).orElseThrow(() -> AppException.notFound("Post Point not found"));
        user.setPostPointId(signUpRequest.getPostPointId());
        return userRepository.save(user);
    }

    public User updateUserInformation(SignUpRequest signUpRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> AppException.notFound("User not found"));
        user.setFirstName(signUpRequest.getFirstName());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(signUpRequest.getRole());
        user.setPhone(signUpRequest.getPhone());
        PostPoint postPoint = postPointRepository.findById(signUpRequest.getPostPointId()).orElseThrow(() -> AppException.notFound("Post Point not found"));
        user.setPostPointId(signUpRequest.getPostPointId());
        return userRepository.save(user);
    }

    public User getUserLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = ( (User) authentication.getPrincipal() ).getEmail();
        return userRepository.findByEmail(currentUserEmail).orElseThrow(() -> AppException.notFound("User not found"));
    }
}
