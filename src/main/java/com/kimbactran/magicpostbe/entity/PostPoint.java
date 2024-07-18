package com.kimbactran.magicpostbe.entity;

import lombok.Data;

import javax.persistence.*;

@Data
//@Entity
public class PostPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pointCode;
    private String pointName;
//    @ManyToOne
//    @JoinColumn(name="address_id")
//    private Address pointLocation;
    private String pointNumber;
    private String pointEmail;
    private String pointAddress;
    private String pointPhone;
    private PointType pointType;

}
