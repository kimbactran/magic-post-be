package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.entity.enumtype.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    List<User> findByRole(UserRole role);

    boolean existsByEmail(String email);
}
