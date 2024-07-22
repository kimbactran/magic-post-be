package com.kimbactran.magicpostbe.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class Sender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long senderId;
    private String firstName;
    private String lastName;
    private String phone;
    private String senderCountry;
    private String senderProvince;
    private String senderDistrict;
    private String senderCommune;
    private String senderDetailAddress;
    private String pointCode;
}
