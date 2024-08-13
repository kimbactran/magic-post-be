package com.kimbactran.magicpostbe.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.kimbactran.magicpostbe.exception.AppException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.spire.xls.Worksheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.spire.xls.Workbook;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class PdfHandler {
    public ByteArrayInputStream exportPdfFinal() throws Exception {


        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("orderCode", "1");
        context.setVariable("logo", "src/main/resources/qrCodeImg/magicpostlogo.png");

        String html = templateEngine.process("MagicPostTemplate", context);


        return new ByteArrayInputStream(convertHtmlToPdf(html));

    }

    public byte[] convertHtmlToPdf(String html) throws Exception {
        byte[] pdfBytes = null;
        try {
            File fontFile = new File("C:\\magic-post-be\\src\\main\\resources\\font\\vuTimesBold.ttf");
            ConverterProperties converterProperties = new ConverterProperties();
            FontProvider fontProvider = new FontProvider();
            fontProvider.getFontSet().addFont(fontFile.getAbsolutePath());
            converterProperties.setFontProvider(fontProvider);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfDocument document = new PdfDocument();
            
            document.setPageSize(PageSize.LETTER.rotate());
            document.setMargins(36, 36, 36, 36);

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            HtmlConverter.convertToPdf(html, writer.getOs(), converterProperties);
            pdfBytes = outputStream.toByteArray();
        } catch (Exception e) {
            throw AppException.badRequest(e.getMessage());
        }
        return pdfBytes;
    }

}
