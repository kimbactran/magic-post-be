package com.kimbactran.magicpostbe.service;

import com.kimbactran.magicpostbe.dto.PostPointDto;
import com.kimbactran.magicpostbe.dto.PostPointPageResponse;
import com.kimbactran.magicpostbe.entity.PointType;
import com.kimbactran.magicpostbe.entity.PostPoint;

import java.util.List;

public interface PostPointService {
    PostPoint createPostPoint(PostPointDto postPoint);
    List<PostPoint> getAllPostPoint();
    PostPoint getPostPointById(Long id);
    PostPoint getPostPointByPointCode(String pointCode);
    PostPoint getPostPointByPointType(PointType type);
    PostPoint updatePostPoint(PostPointDto postPointDto, Long id);
    void deletePostPoint(Long id);
    PostPoint setPointLeader(Long pointLeaderId, Long postPointId);
    PostPointPageResponse getAllPostPointsWithPagination(Integer pageNumber, Integer pageSize);
    PostPointPageResponse getAllPostPointsWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

}
