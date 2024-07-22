package com.kimbactran.magicpostbe.service.serviceimpl;

import com.kimbactran.magicpostbe.dto.UserDto;
import com.kimbactran.magicpostbe.entity.PostPoint;
import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.entity.UserRole;
import com.kimbactran.magicpostbe.exception.AppException;
import com.kimbactran.magicpostbe.repository.PostPointRepository;
import com.kimbactran.magicpostbe.repository.UserRepository;
import com.kimbactran.magicpostbe.service.PostPointService;
import com.kimbactran.magicpostbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final PostPointRepository postPointRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmail(email).orElseThrow(() -> AppException.notFound("User not found"));
            }
        };
    }

//    public User createUser(UserDto userDto) throws AppException {
//        if(userRepository.existsByEmail(userDto.getEmail())) {
//            throw AppException.badRequest("User already exists");
//        }
//        User user = new User();
//        user.setEmail(userDto.getEmail());
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        user.setFirstName(userDto.getFirstName());
//        user.setLastName(userDto.getLastName());
//        user.setRole(userDto.getRole());
//        user.setPhone(userDto.getPhone());
//        user.setUsername(userDto.getFirstName() + userDto.getLastName());
//        PostPoint postPoint = postPointRepository.findById(userDto.getPostPointId()).orElseThrow(() -> AppException.notFound("Post Point not found"));
//        user.setPostPointId(userDto.getPostPointId());
//        User savedUser = userRepository.save(user);
//        postPoint.setPointLeaderId(savedUser.getId());
//        return savedUser;
//    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public List<User> getUserByRole(UserRole role){
        return userRepository.findByRole(role);
    }

    public void deleteUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> AppException.notFound("User not found"));
        userRepository.delete(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> AppException.notFound("User not found"));
    }
}
