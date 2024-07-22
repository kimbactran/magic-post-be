package com.kimbactran.magicpostbe.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_code", updatable = false, nullable = false)
    private String orderCode;

    private String userSendId;
    private String userReceiverId;

    private OrderType orderType;

    private String orderValue;
    private String additionalService;
    private Date orderDate = new Date();
    private Date dateReceived;
    private String orderStatus;
    private String orderWeight;
    private UserPayment userPayment;
    private enum UserPayment {
        SENDER, RECEIVER
    }

    private String orderDescription;
    private String cancellationReason;
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
    @PrePersist
    public void generateOrderCode() {
        this.orderCode = "ORDERMGP" + this.id;
    }
}
