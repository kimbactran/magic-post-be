package com.kimbactran.magicpostbe.dto;

import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.kimbactran.magicpostbe.entity.OrderType;

import java.util.Date;

public class OderRequest {

    private String firstNameSender;
    private String lastNameSender;
    private String phoneSender;
    private String customerCountrySender;
    private String customerProvinceSender;
    private String customerDistrictSender;
    private String customerCommuneSender;
    private String customerDetailAddressSender;
    private String pointCodeSender;
    private String userReceiverIdSender;

    private String firstNameReceiver;
    private String lastNameReceiver;
    private String emailReceiver;
    private String phoneReceiver;
    private String customerCountryReceiver;
    private String customerProvinceReceiver;
    private String customerDistrictReceiver;
    private String customerCommuneReceiver;
    private String customerDetailAddressReceiver;
    private String pointCodeReceiver;
    private String userReceiverIdSReceiver;

    private OrderType orderType;

    // So tien hang gui
    private String orderValue;
    private String additionalService;
    private String orderStatus;
    private String orderWeight;
    private UserPayment userPayment;
    private enum UserPayment {
        SENDER, RECEIVER
    }

    private String orderDescription;
    private String mainCharge;
    private String surCharge;
    private String totalCharge;

    private String transactionPointSender;
    private String gatherPointSender;
    private String transactionPointReceiver;
    private String gatherPointReceiver;

    private String currentPoint;
    // 1. Tiep nhan
    // 2. Cho tiep nhan
    private String statusPointOrder;
}
