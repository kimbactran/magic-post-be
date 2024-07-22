package com.kimbactran.magicpostbe.service.serviceimpl;

import com.kimbactran.magicpostbe.dto.OderRequest;
import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.kimbactran.magicpostbe.repository.OrderRepository;
import com.kimbactran.magicpostbe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    public OrderInfo createOrder(OderRequest orderRequest) {
        OrderInfo orderInfo = new OrderInfo();


        return orderRepository.save(orderInfo);
    }
}
