package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SenderRepository extends JpaRepository<Sender, Long> {
    Optional<Sender> findByPhone(String phone);
}
