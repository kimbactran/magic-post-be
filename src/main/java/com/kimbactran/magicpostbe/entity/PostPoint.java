package com.kimbactran.magicpostbe.entity;

import com.kimbactran.magicpostbe.entity.enumtype.PointType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post_point")
public class PostPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pointCode;
    private String pointName;
    private String pointCountry;
    private String pointProvince;
    private String pointDistrict;
    private String pointCommune;
    private String pointDetailAddress;
    private String pointNumber;
    private String pointEmail;
    private String pointPhone;
    private PointType pointType;
    private Long pointLeaderId;
    private Long pointGatherId;
}
