package com.kimbactran.magicpostbe.dto;

import lombok.Data;

@Data
public class ReceiverRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String receiverCountry;
    private String receiverProvince;
    private String receiverDistrict;
    private String receiverCommune;
    private String receiverDetailAddress;
}
