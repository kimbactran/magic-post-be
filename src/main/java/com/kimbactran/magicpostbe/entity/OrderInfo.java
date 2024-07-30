package com.kimbactran.magicpostbe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", updatable = false, nullable = false)
    private String orderCode;

    private Long createUser;
    private Long orderCustomerId;
    private Long userSendId;
    private Long userReceiverId;

    private OrderType orderType;

    private String orderValue;
    private String additionalService;
    private Date orderDate = new Date();
    private Date dateReceived;
    private OrderStatus orderStatus;
    private String orderWeight;
    private UserPayment userPayment;
    private String orderDescription;
    private String cancellationReason;
    private String mainCharge;
    private String surCharge;
    private String totalCharge;

    private Long transactionPointSender;
    private Long gatherPointSender;
    private Long transactionPointReceiver;
    private Long gatherPointReceiver;
    private SenderInstructions senderInstructions;

    private Long currentPoint;
    // 1. Tiep nhan
    // 2. Cho tiep nhan
    private StatusPointOrder statusPointOrder;
    @PrePersist
    public void generateOrderCode() {
        this.orderCode = "ORDERMGP" + this.id;
    }
}
