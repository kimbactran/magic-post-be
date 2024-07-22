package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Long> {
}
