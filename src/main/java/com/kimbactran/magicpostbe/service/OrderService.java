package com.kimbactran.magicpostbe.service;

import com.kimbactran.magicpostbe.dto.OderRequest;
import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.kimbactran.magicpostbe.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderInfo createOrder(OderRequest orderRequest);
    int getTotalOrder();
    int getTotalOrderByOrderStatus(OrderStatus orderStatus);
    List<OrderInfo> getTotalOrderByOrderPointId(Long postPointId);
    List<OrderInfo> getAllOrder();
}
