package com.kimbactran.magicpostbe.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String customerCountry;
    private String customerProvince;
    private String customerDistrict;
    private String customerCommune;
    private String customerDetailAddress;
    private String pointCode;
}
