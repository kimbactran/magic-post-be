package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.PointType;
import com.kimbactran.magicpostbe.entity.PostPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PostPointRepository extends JpaRepository<PostPoint, Long> {
    Optional<PostPoint> findByPointCode(String pointCode);

    boolean existsByPointCode(String pointCode);

    Optional<PostPoint> findByPointType(PointType type);

    PostPoint findByPointCommune(String commune);

    PostPoint findByPointDistrict(String district);

    PostPoint findByPointProvince(String province);

}
