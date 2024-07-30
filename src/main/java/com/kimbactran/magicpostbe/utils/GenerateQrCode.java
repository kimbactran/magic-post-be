package com.kimbactran.magicpostbe.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kimbactran.magicpostbe.dao.OrderExportExcelDao;
import com.kimbactran.magicpostbe.entity.OrderInfo;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Component
public class GenerateQrCode {
    public void generateQrCode(OrderExportExcelDao orderInfo) throws WriterException, IOException {
        String qrCodePath = "D:\\QRImg\\";
        String qrCodeName = qrCodePath + orderInfo.getId() + ".png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode("Order Id: "+ orderInfo.getId() + "\n"
                + "Order Customer Id: " + orderInfo.getOrderCustomerId()+ "\n"
                + "Order Create User: " + orderInfo.getCreateUser() + "\n"
                + "Order Sender Id: " + orderInfo.getUserSendId() + "\n"
                + "Order Receiver Id: "+ orderInfo.getUserReceiverId() + "\n"
                + "Order Type: " + orderInfo.getOrderType() + "\n"
                + "Order Value" + orderInfo.getOrderValue() + "\n",
                BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public void generateQrCodeExample() throws WriterException, IOException {
        String qrCodePath = "D:\\QRImg\\";
        String qrCodeName = qrCodePath + "example.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode("Order Id: "+  "\n"
                        + "Order Customer Id: " + "\n"
                        + "Order Create User: " + "\n"
                        + "Order Sender Id: "  + "\n"
                        + "Order Receiver Id: "+ "\n"
                        + "Order Type: " + "\n"
                        + "Order Value"  + "\n",
                BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public BitMatrix generateQRCodeForExcel(OrderExportExcelDao orderExportExcelDao) throws WriterException, IOException {
        var qrCodeWriter = new QRCodeWriter();
        return qrCodeWriter.encode("Order Id: "+  "\n"
                        + "Order Customer Id: " + "\n"
                        + "Order Create User: " + "\n"
                        + "Order Sender Id: "  + "\n"
                        + "Order Receiver Id: "+ "\n"
                        + "Order Type: " + "\n"
                        + "Order Value"  + "\n",
                BarcodeFormat.QR_CODE, 400, 400);
    }
}
