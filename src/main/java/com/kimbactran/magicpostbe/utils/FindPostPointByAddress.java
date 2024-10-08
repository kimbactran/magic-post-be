package com.kimbactran.magicpostbe.utils;

import com.kimbactran.magicpostbe.entity.enumtype.PointType;
import com.kimbactran.magicpostbe.entity.PostPoint;
import com.kimbactran.magicpostbe.exception.AppException;
import com.kimbactran.magicpostbe.repository.PostPointRepository;
import org.springframework.stereotype.Component;

@Component
public class FindPostPointByAddress {
    private PostPointRepository postPointRepository;
    public PostPoint getPostPointByAddress(String province, String district, String commune) {
        PostPoint postPoint = postPointRepository.findByPointCommune(commune).orElseThrow(() -> AppException.badRequest("PostPoint not found"));
        if(postPoint != null && postPoint.getPointType() == PointType.TRANSACTION_POINT) {
            return postPoint;
        } else {
            PostPoint postPointD = postPointRepository.findByPointDistrict(district).orElseThrow(() -> AppException.badRequest("PostPoint not found"));
            if(postPointD != null && postPointD.getPointType() == PointType.TRANSACTION_POINT) {
                return postPointD;
            } else {
                PostPoint postPointP = postPointRepository.findByPointProvince(province).orElseThrow(() -> AppException.badRequest("PostPoint not found"));
                if(postPointP != null && postPointP.getPointType() == PointType.TRANSACTION_POINT) {
                    return postPointP;
                }
            }
        }

        return new PostPoint();
    }
}
