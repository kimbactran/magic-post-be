package com.kimbactran.magicpostbe.dto;

import com.kimbactran.magicpostbe.entity.enumtype.OrderType;
import com.kimbactran.magicpostbe.entity.enumtype.UserPayment;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderRequest {

    private Long orderCustomerId;

    private String firstNameReceiver;
    private String lastNameReceiver;
    private String phoneReceiver;
    private String countryReceiver;
    private String provinceReceiver;
    private String districtReceiver;
    private String communeReceiver;
    private String detailAddressReceiver;
    private String pointCodeReceiver;

    private OrderType orderType;

    // So tien hang gui
    private String orderValue;
    private String additionalService;
    private String orderWeight;
    private UserPayment userPayment;
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
