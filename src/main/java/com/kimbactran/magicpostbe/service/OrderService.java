package com.kimbactran.magicpostbe.service;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.kimbactran.magicpostbe.dto.OderRequest;
import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.kimbactran.magicpostbe.entity.OrderStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface OrderService {
    OrderInfo createOrder(OderRequest orderRequest);
    int getTotalOrder();
    int getTotalOrderByOrderStatus(OrderStatus orderStatus);
    List<OrderInfo> getTotalOrderByOrderPointId(Long postPointId);
    List<OrderInfo> getAllOrder();
    ResponseEntity<?> exportPdfOrder(OrderInfo orderInfo) throws IOException, WriterException;
    ResponseEntity<?> exportPdfOrderEx() throws Exception;
    void exportPdfOrderEx(HttpServletResponse response) throws Exception;
}
