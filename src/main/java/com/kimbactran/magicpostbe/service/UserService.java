package com.kimbactran.magicpostbe.service;

import com.kimbactran.magicpostbe.dto.JwtAuthenticationResponse;
import com.kimbactran.magicpostbe.dto.SignInRequest;
import com.kimbactran.magicpostbe.dto.UserDto;
import com.kimbactran.magicpostbe.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService{
    UserDetailsService userDetailsService();
//    User createUser(UserDto userDto);

}
