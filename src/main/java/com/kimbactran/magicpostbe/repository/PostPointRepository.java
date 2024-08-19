package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.enumtype.PointType;
import com.kimbactran.magicpostbe.entity.PostPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PostPointRepository extends JpaRepository<PostPoint, Long> {
    Optional<PostPoint> findByPointCode(String pointCode);

    boolean existsByPointCode(String pointCode);

    Optional<PostPoint> findByPointType(PointType type);

    Optional<PostPoint> findByPointCommune(String commune);

    Optional<PostPoint> findByPointDistrict(String district);

    Optional<PostPoint> findByPointProvince(String province);

    Optional<PostPoint> findByPointCountry(String country);

    @Query(value = "SELECT p FROM PostPoint p " +
            "WHERE (:commune is null or p.pointCommune like %:commune%)" +
            "and (:district is null or p.pointDistrict like %:district%)" +
            "and (:province is null or p.pointProvince like %:province%)" +
            "and (:country is null or p.pointProvince like %:country%)")
    Optional<PostPoint> findPostPointByAddress(@Param("commune") String commune,
                                               @Param("district") String district,
                                               @Param("province") String province,
                                               @Param("country") String country);
}
