package com.kimbactran.magicpostbe.dao;

import com.kimbactran.magicpostbe.entity.enumtype.OrderType;
import com.kimbactran.magicpostbe.entity.enumtype.SenderInstructions;
import lombok.Data;

import java.util.Date;

@Data
public class OrderExportExcelDao {
    private Long id;
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

}
