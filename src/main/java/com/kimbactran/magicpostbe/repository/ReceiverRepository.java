package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

    boolean exitsByPhone(String phoneReceiver);
    Receiver findByPhone(String phoneReceiver);
}
