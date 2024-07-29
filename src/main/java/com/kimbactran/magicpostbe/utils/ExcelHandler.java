package com.kimbactran.magicpostbe.utils;

import com.kimbactran.magicpostbe.entity.OrderInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class ExcelHandler {
    private final Integer dataLength = 29;
    public void exportExcelOrder(String imgFilePath, OrderInfo orderInfo) throws IOException {
        XSSFWorkbook newWorkbook;
        try {
            InputStream inputStream = new ClassPathResource("/template/MagicPostOrderTemplate.xlsx", this.getClass()).getInputStream();
            newWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Sheet sheet = newWorkbook.getSheetAt(1);
        Row row = sheet.getRow(1);
        for(int rowNum = 0; rowNum <= dataLength; rowNum++) {
            Cell cell = row.getCell(rowNum);
            switch(rowNum){
                case 0: cell.setCellValue(orderInfo.getOrderCode());
                case 1: {
                    byte[] imageBytes = convertImgToByteArray(imgFilePath);

                    // Add image to the Excel file
                    int pictureIndex = newWorkbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
                    CreationHelper createHelper = newWorkbook.getCreationHelper();
                    Drawing<?> drawing = sheet.createDrawingPatriarch();
                    ClientAnchor anchor = createHelper.createClientAnchor();
                    anchor.setCol1(cell.getColumnIndex());
                    anchor.setRow1(rowNum);
                    drawing.createPicture(anchor, pictureIndex);
                }
                case 2: cell.setCellValue();
            }
        }
    }


    private byte[] convertImgToByteArray(String imgFilePath) throws IOException {
        // Load Qr Img code
        BufferedImage qrImg = ImageIO.read(new File(imgFilePath));

        // Convert Img to bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImg, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
