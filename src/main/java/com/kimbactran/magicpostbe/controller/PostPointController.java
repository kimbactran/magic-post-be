package com.kimbactran.magicpostbe.controller;

import com.kimbactran.magicpostbe.dto.PostPointDto;
import com.kimbactran.magicpostbe.entity.PostPoint;
import com.kimbactran.magicpostbe.service.PostPointService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/postPoint")
@RequiredArgsConstructor
public class PostPointController {
    private PostPointService postPointService;
    @PostMapping("/create")
    public ResponseEntity<PostPoint> createPostPoint(@RequestBody PostPointDto postPoint) {
        return ResponseEntity.ok(postPointService.createPostPoint(postPoint));
    }
}
