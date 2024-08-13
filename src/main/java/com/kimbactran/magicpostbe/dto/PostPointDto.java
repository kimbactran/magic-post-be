package com.kimbactran.magicpostbe.dto;

import com.kimbactran.magicpostbe.entity.enumtype.PointType;
import lombok.Data;

@Data
public class PostPointDto {
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
}
