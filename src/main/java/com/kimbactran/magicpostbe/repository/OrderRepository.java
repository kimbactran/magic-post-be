package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.kimbactran.magicpostbe.entity.enumtype.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Long> {
    int countByOrderStatus(OrderStatus orderStatus);

    List<OrderInfo> findByCurrentPoint(Long postPointId);
}
