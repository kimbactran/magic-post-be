package com.kimbactran.magicpostbe.dao;

import com.kimbactran.magicpostbe.entity.*;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class OrderExportExcelDao {
    private String orderCode;

    private Long createUser;
    private String createUserName;
    private Long orderCustomerId;
    private String orderCustomerCode;
    private Long userSendId;
    private String userSenderName;
    private String userSenderPhoneNumber;
    private String userSenderAddress;
    private Long userReceiverId;
    private String userReceiverName;
    private String userReceiverPhoneNumber;
    private String userReceiverAddress;

    private OrderType orderType;

    private String orderValue;
    private String additionalService;
    private Date orderDate;
    private SenderInstructions senderInstructions;
    private String orderWeight;
    private String orderDescription;
    private String mainCharge;
    private String surCharge;
    private String totalCharge;

    private Long transactionPointSender;
    private String transactionPointSenderName;
    private Long gatherPointSender;
    private Long transactionPointReceiver;
    private String transactionPointReceiverName;
    private Long gatherPointReceiver;

    private Long currentPoint;
    // 1. Tiep nhan
    // 2. Cho tiep nhan
    private StatusPointOrder statusPointOrder;
}
