package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

    boolean existsByPhone(String phoneReceiver);
    Optional<Receiver> findByPhone(String phoneReceiver);
}
