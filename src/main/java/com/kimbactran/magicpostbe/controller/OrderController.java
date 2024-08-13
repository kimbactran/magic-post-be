package com.kimbactran.magicpostbe.controller;

import com.google.zxing.WriterException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
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


    @GetMapping("/getExcelOrder")
    public void getExcelOrder(HttpServletResponse response) throws Exception {
        ByteArrayInputStream byteArrayInputStream = pdfHandler.exportPdfFinal();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=file2.pdf");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }

    @GetMapping("/generate-pdf")
    public void generatePDF(HttpServletResponse response) throws IOException, DocumentException {
        // Lấy dữ liệu cần in
        Map<String, Object> model = new HashMap<>();
        model.put("username", "John Doe");
        model.put("email", "john.doe@example.com");

        // Tạo PDF từ Thymeleaf template
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        TemplateEngine templateEngine = new TemplateEngine();
        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process("MagicPostOrderTemplate", context);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(html));
        document.close();

        // Thiết lập header để trình duyệt tải PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
    }


}
