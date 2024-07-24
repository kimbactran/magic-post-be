package com.kimbactran.magicpostbe.controller;

import com.google.zxing.WriterException;
import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.kimbactran.magicpostbe.service.OrderService;
import com.kimbactran.magicpostbe.utils.GenerateQrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final GenerateQrCode generateQrCode;

    @GetMapping("/allOrder")
    public List<OrderInfo> getAllOrder() throws IOException, WriterException {
        generateQrCode.generateQrCodeExample();
        return orderService.getAllOrder();
    }

}
