package com.kimbactran.magicpostbe.service;

import com.kimbactran.magicpostbe.dto.PostPointDto;
import com.kimbactran.magicpostbe.entity.PostPoint;

public interface PostPointService {
    PostPoint createPostPoint(PostPointDto postPoint);
}
