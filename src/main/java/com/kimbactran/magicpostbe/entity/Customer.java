package com.kimbactran.magicpostbe.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
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
