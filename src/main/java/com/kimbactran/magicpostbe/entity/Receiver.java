package com.kimbactran.magicpostbe.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor

public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long receiverId;
    private String firstName;
    private String lastName;
    private String phone;
    private String receiverCountry;
    private String receiverProvince;
    private String receiverDistrict;
    private String receiverCommune;
    private String receiverDetailAddress;
    private String pointCode;

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    public String getAddress(){
        return this.receiverDetailAddress + " - " + this.receiverCommune + this.receiverDistrict + " - " + this.receiverProvince + " - " + this.receiverCountry;
    }
}
