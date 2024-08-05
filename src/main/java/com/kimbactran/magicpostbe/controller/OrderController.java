package com.kimbactran.magicpostbe.controller;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.kimbactran.magicpostbe.service.OrderService;
import com.kimbactran.magicpostbe.utils.GenerateQrCode;
import com.kimbactran.magicpostbe.utils.PdfHandler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

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


    @GetMapping("/getExcelOrder")
    public void getExcelOrder(HttpServletResponse response) throws Exception {
        ByteArrayInputStream byteArrayInputStream = pdfHandler.exportPdfFinal();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=file.pdf");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }


}
