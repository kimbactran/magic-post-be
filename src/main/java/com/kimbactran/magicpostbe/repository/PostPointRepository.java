package com.kimbactran.magicpostbe.repository;

import com.kimbactran.magicpostbe.entity.PointType;
import com.kimbactran.magicpostbe.entity.PostPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostPointRepository extends JpaRepository<PostPoint, Long> {
    Optional<PostPoint> findByPointCode(String pointCode);

    boolean existsByPointCode(String pointCode);

    Optional<PostPoint> findByPointType(PointType type);
}
