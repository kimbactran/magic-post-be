package com.kimbactran.magicpostbe.dto;

import com.kimbactran.magicpostbe.entity.PostPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class PostPointPageResponse {
    List<PostPoint> postPoints;
    Integer pageNumber;
    Integer pageSize;
    int totalElements;
    int totalPages;
    boolean isLast;
}
