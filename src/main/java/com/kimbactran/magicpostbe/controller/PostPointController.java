package com.kimbactran.magicpostbe.controller;

import com.kimbactran.magicpostbe.dto.PostPointDto;
import com.kimbactran.magicpostbe.entity.PointType;
import com.kimbactran.magicpostbe.entity.PostPoint;
import com.kimbactran.magicpostbe.service.PostPointService;
import com.kimbactran.magicpostbe.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/postPoint")
@RequiredArgsConstructor
public class PostPointController {
    @Autowired
    private PostPointService postPointService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createPostPoint")
    public ResponseEntity<PostPoint> createPostPoint(@RequestBody PostPointDto postPoint) {
        return ResponseEntity.ok(postPointService.createPostPoint(postPoint));
    }

    @GetMapping("/getAllPostPoint")
    public ResponseEntity<List<PostPoint>> getAllPostPoint() {
        return ResponseEntity.ok(postPointService.getAllPostPoint());
    }

    @GetMapping("/getPostPointWithPagination")
    public ResponseEntity<?> getPostPointWithPagination(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                        @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        return ResponseEntity.ok(postPointService.getAllPostPointsWithPagination(pageNumber, pageSize));
    }

    @GetMapping("/getPostPointWithPaginationAndSort")
    public ResponseEntity<?> getPostPointWithPaginationAndSort(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                               @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                               @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                               @RequestParam(defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){
        return ResponseEntity.ok(postPointService.getAllPostPointsWithPaginationAndSorting(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/getPostPointById/{id}")
    public ResponseEntity<PostPoint> getPostPointById(@PathVariable Long id) {
        return ResponseEntity.ok(postPointService.getPostPointById(id));
    }

    @GetMapping("/getPostPointByPointCode/{pointCode}")
    public ResponseEntity<PostPoint> getPostPointByPointCode(@PathVariable String pointCode) {
        return ResponseEntity.ok(postPointService.getPostPointByPointCode(pointCode));
    }

    @GetMapping("/getPostPointByPointType/{pointType}")
    public ResponseEntity<PostPoint> getPostPointByPointType(@PathVariable PointType pointType) {
        return ResponseEntity.ok(postPointService.getPostPointByPointType(pointType));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updatePostPoint/{id}")
    public ResponseEntity<PostPoint> updatePostPoint(@RequestBody PostPointDto postPoint, @PathVariable Long id) {
        return ResponseEntity.ok(postPointService.updatePostPoint(postPoint, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletePostPoint/{id}")
    public ResponseEntity<String> deletePostPoint(@PathVariable Long id) {
        postPointService.deletePostPoint(id);
        return ResponseEntity.ok("Delete post point id" + id + "successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/setPointLeader")
    public ResponseEntity<PostPoint> setPointLeader(@RequestBody Long pointLeaderId, @RequestBody Long postPointId) {
        return ResponseEntity.ok(postPointService.setPointLeader(pointLeaderId, postPointId));
    }

}
