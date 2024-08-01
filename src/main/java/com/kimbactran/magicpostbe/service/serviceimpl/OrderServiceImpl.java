package com.kimbactran.magicpostbe.service.serviceimpl;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.kimbactran.magicpostbe.dao.OrderExportExcelDao;
import com.kimbactran.magicpostbe.dto.OderRequest;
import com.kimbactran.magicpostbe.dto.Response;
import com.kimbactran.magicpostbe.entity.*;
import com.kimbactran.magicpostbe.exception.AppException;
import com.kimbactran.magicpostbe.mapper.MappingHelper;
import com.kimbactran.magicpostbe.repository.*;
import com.kimbactran.magicpostbe.service.OrderService;
import com.kimbactran.magicpostbe.utils.ExcelHandler;
import com.kimbactran.magicpostbe.utils.FindPostPointByAddress;
import com.kimbactran.magicpostbe.utils.GenerateQrCode;
import com.kimbactran.magicpostbe.utils.PdfHandler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ReceiverRepository receiverRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SenderRepository senderRepository;
    private final UserRepository userRepository;
    private final PostPointRepository postPointRepository;
    private final FindPostPointByAddress findPostPointByAddress;
    private final GenerateQrCode generateQrCode;
    private final MappingHelper mappingHelper;
    private final ExcelHandler excelHandler;
    private final PdfHandler pdfHandler;


    public OrderInfo createOrder(OderRequest orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElse(null);
        OrderInfo orderInfo = new OrderInfo();
        Customer customer = customerRepository.findById(orderRequest.getOrderCustomerId()).orElseThrow(() -> new AppException("Customer not found"));
        assert currentUser != null;
        orderInfo.setCreateUser(currentUser.getId());
        orderInfo.setOrderCustomerId(customer.getId());
        PostPoint currentPostPoint = postPointRepository.findById(currentUser.getPostPointId()).orElseThrow(() -> new AppException("PostPoint not found"));
        // Get Sender with Customer Id
        Sender sender = senderRepository.findByPhone(customer.getPhone()).orElseThrow(() -> new AppException("Sender not found"));
        orderInfo.setUserSendId(sender.getSenderId());

        if(receiverRepository.existsByPhone(orderRequest.getPhoneReceiver())){
            Receiver receiver = receiverRepository.findByPhone(orderRequest.getPhoneReceiver());
            orderInfo.setUserReceiverId(receiver.getReceiverId());
        } else {
            Receiver receiver = new Receiver();
            receiver.setPhone(orderRequest.getPhoneReceiver());
            receiver.setFirstName(orderRequest.getFirstNameReceiver());
            receiver.setLastName(orderRequest.getLastNameReceiver());
            receiver.setReceiverCountry(orderRequest.getCountryReceiver());
            receiver.setReceiverProvince(orderRequest.getProvinceReceiver());
            receiver.setReceiverDistrict(orderRequest.getDistrictReceiver());
            receiver.setReceiverCommune(orderRequest.getCommuneReceiver());
            receiver.setReceiverDetailAddress(orderRequest.getDetailAddressReceiver());
            receiver.setPointCode(orderRequest.getPointCodeReceiver());
            Receiver savedReceiver = receiverRepository.save(receiver);
            orderInfo.setUserReceiverId(savedReceiver.getReceiverId());
        }

        orderInfo.setOrderType(orderRequest.getOrderType());
        orderInfo.setOrderValue(orderRequest.getOrderValue());
        orderInfo.setAdditionalService(orderRequest.getAdditionalService());
        orderInfo.setOrderStatus(OrderStatus.ORDER_00);
        orderInfo.setOrderWeight(orderRequest.getOrderWeight());
        orderInfo.setUserPayment(orderRequest.getUserPayment());
        orderInfo.setOrderDescription(orderRequest.getOrderDescription());
        orderInfo.setMainCharge(orderRequest.getMainCharge());
        orderInfo.setSurCharge(orderRequest.getSurCharge());
        orderInfo.setTotalCharge(orderRequest.getTotalCharge());
        orderInfo.setCurrentPoint(currentPostPoint.getId());
        orderInfo.setTransactionPointSender(currentPostPoint.getId());
        orderInfo.setGatherPointSender(currentPostPoint.getPointGatherId());
        PostPoint senderPostPoint = findPostPointByAddress.getPostPointByAddress(orderRequest.getProvinceReceiver(), orderRequest.getDistrictReceiver(), orderRequest.getCommuneReceiver());
        if(senderPostPoint != null){
            orderInfo.setTransactionPointSender(senderPostPoint.getId());
            orderInfo.setGatherPointSender(senderPostPoint.getPointGatherId());
        }
        orderInfo.setStatusPointOrder(StatusPointOrder.S1);
        return orderRepository.save(orderInfo);
    }

    public int getTotalOrder(){
        return orderRepository.findAll().size();
    }

    public int getTotalOrderByOrderStatus(OrderStatus orderStatus){
        return orderRepository.countByOrderStatus(orderStatus);
    }

    public List<OrderInfo> getTotalOrderByOrderPointId(Long postPointId){
        return orderRepository.findByCurrentPoint(postPointId);
    }

    public List<OrderInfo> getAllOrder() {
        return orderRepository.findAll();
    }

    public ResponseEntity<?> exportPdfOrder(OrderInfo orderInfo) throws IOException, WriterException {
        OrderExportExcelDao orderExportExcelDao = convertToDao(orderInfo);
        generateQrCode.generateQrCode(orderExportExcelDao);
        String imgPath = "D:\\QRImg\\" + orderInfo.getId() + ".png";
        XSSFWorkbook workbook = excelHandler.exportExcelOrder(imgPath, orderExportExcelDao);
        return export(workbook, String.valueOf(orderInfo.getId()));
    }

    public ResponseEntity<?> exportPdfOrderEx() throws Exception {
//        generateQrCode.generateQrCodeExample();
//        String imgPath = "D:\\QRImg\\" + "example.png";
//        XSSFWorkbook workbook = excelHandler.exportExcelOrderEx(imgPath);
//        pdfHandler.convertExcelToPdf();
//        pdfHandler.exportToPdf();

//        pdfHandler.convertExcelToPdfV2();
        pdfHandler.exportPdfFinal();
//        pdfHandler.exportPdfFinal();
//        return export(workbook, "example");
        return ResponseEntity.ok("Success");
    }

    private OrderExportExcelDao convertToDao(OrderInfo orderInfo) {
        OrderExportExcelDao orderExportExcelDao = mappingHelper.map(orderInfo, OrderExportExcelDao.class);
        Sender sender = senderRepository.findById(orderInfo.getUserSendId()).orElseThrow(() -> new AppException("Sender not found"));
        orderExportExcelDao.setUserSenderName(sender.getFullName());
        orderExportExcelDao.setUserSenderAddress(sender.getAddress());
        orderExportExcelDao.setUserSenderPhoneNumber(sender.getPhone());

        Receiver receiver = receiverRepository.findById(orderInfo.getUserReceiverId()).orElseThrow(() -> new AppException("Receiver not found"));
        orderExportExcelDao.setUserSenderName(receiver.getFullName());
        orderExportExcelDao.setUserSenderAddress(receiver.getAddress());
        orderExportExcelDao.setUserSenderPhoneNumber(receiver.getPhone());

        PostPoint transactionPointSender = postPointRepository.findById(orderInfo.getTransactionPointSender()).orElseThrow(() -> new AppException("TransactionPointSender not found"));
        orderExportExcelDao.setTransactionPointSenderName(transactionPointSender.getPointName());

        PostPoint transactionPointReceiver = postPointRepository.findById(orderInfo.getTransactionPointReceiver()).orElseThrow(() -> new AppException("TransactionPointReceiver not found"));
        orderExportExcelDao.setTransactionPointReceiverName(transactionPointReceiver.getPointName());

        return orderExportExcelDao;
    }

    public static ResponseEntity<?> export(XSSFWorkbook newWorkbook, String fileName) {
        List<String> allows = Arrays.asList("code", "message");
        HttpHeaders header;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            header = new HttpHeaders();
            header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            header.set("Content-disposition", "attachment; filename=" + fileName);
            header.set("message", "Success");
            header.set("code", "200");
            header.setAccessControlExposeHeaders(allows);
            newWorkbook.write(stream);
            newWorkbook.close();
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()), header, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            header = new HttpHeaders();
            header.set("message", "Failed");
            header.set("code", "400");
            header.setAccessControlExposeHeaders(allows);
            return ResponseEntity.ok("Success");
        }
    }
}
