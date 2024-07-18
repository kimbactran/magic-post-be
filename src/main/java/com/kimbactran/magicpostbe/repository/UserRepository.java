package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByRole(UserRole role);

    boolean existsByEmail(String email);
}
