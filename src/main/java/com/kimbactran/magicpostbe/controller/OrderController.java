package com.kimbactran.magicpostbe.controller;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.kimbactran.magicpostbe.dto.OrderRequest;
import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.kimbactran.magicpostbe.service.OrderService;
import com.kimbactran.magicpostbe.utils.GenerateQrCode;
import com.kimbactran.magicpostbe.utils.PdfHandler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final GenerateQrCode generateQrCode;
    private final PdfHandler pdfHandler;

    @GetMapping("/allOrder")
    public List<OrderInfo> getAllOrder() throws IOException, WriterException {
        return orderService.getAllOrder();
    }


    @GetMapping("/getPdf")
    public void getPdf(HttpServletResponse response) throws Exception {
        orderService.getOrderPdf(response);
    }


    @PostMapping("/create")
    public OrderInfo createOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        return orderService.createOrder(orderRequest);
    }

    @PostMapping("/update/{orderId}")
    public OrderInfo updateOrder(@RequestBody OrderRequest orderRequest, @PathVariable Long orderId) throws Exception {
        return orderService.updateOrderInfo(orderRequest, orderId);
    }
}
