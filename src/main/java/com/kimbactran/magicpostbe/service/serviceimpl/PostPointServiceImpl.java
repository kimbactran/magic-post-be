package com.kimbactran.magicpostbe.service.serviceimpl;

import com.kimbactran.magicpostbe.dto.PostPointDto;
import com.kimbactran.magicpostbe.dto.PostPointPageResponse;
import com.kimbactran.magicpostbe.entity.PointType;
import com.kimbactran.magicpostbe.entity.PostPoint;
import com.kimbactran.magicpostbe.exception.AppException;
import com.kimbactran.magicpostbe.repository.PostPointRepository;
import com.kimbactran.magicpostbe.repository.UserRepository;
import com.kimbactran.magicpostbe.service.PostPointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostPointServiceImpl implements PostPointService {
    private PostPointRepository postPointRepository;
    private UserRepository userRepository;

    public PostPoint createPostPoint(PostPointDto postPointDto) {
        if(postPointRepository.existsByPointCode(postPointDto.getPointCode())) {
            throw AppException.dataExists("Post Point was exists!");
        }
        PostPoint postPoint = new PostPoint();
        postPoint.setPointCode(postPointDto.getPointCode());
        postPoint.setPointName(postPointDto.getPointName());
        postPoint.setPointCountry(postPointDto.getPointCountry());
        postPoint.setPointProvince(postPointDto.getPointProvince());
        postPoint.setPointDistrict(postPoint.getPointDistrict());
        postPoint.setPointCommune(postPointDto.getPointCommune());
        postPoint.setPointDetailAddress(postPointDto.getPointDetailAddress());
        postPoint.setPointNumber(postPointDto.getPointNumber());
        postPoint.setPointEmail(postPointDto.getPointEmail());
        postPoint.setPointPhone(postPointDto.getPointPhone());
        postPoint.setPointType(postPointDto.getPointType());
        return postPointRepository.save(postPoint);
    }


    public List<PostPoint> getAllPostPoint() {
        return postPointRepository.findAll();
    }

    public PostPoint getPostPointById(Long id) {
        return postPointRepository.findById(id).orElseThrow(() -> AppException.notFound("Post Point was not found!"));
    }

    public PostPoint getPostPointByPointCode(String pointCode) {
        return postPointRepository.findByPointCode(pointCode).orElseThrow(() -> AppException.notFound("Post Point was not found!"));
    }

    public PostPoint getPostPointByPointType(PointType type) {
        return postPointRepository.findByPointType(type).orElseThrow(() -> AppException.notFound("Post Point was not found!"));
    }

    public PostPoint updatePostPoint(PostPointDto postPointDto, Long id) {
        PostPoint postPoint = postPointRepository.findById(id).orElseThrow(() -> AppException.notFound("Post Point was not found!"));
        postPoint.setPointCode(postPointDto.getPointCode());
        postPoint.setPointName(postPointDto.getPointName());
        postPoint.setPointCountry(postPointDto.getPointCountry());
        postPoint.setPointProvince(postPointDto.getPointProvince());
        postPoint.setPointDistrict(postPoint.getPointDistrict());
        postPoint.setPointCommune(postPointDto.getPointCommune());
        postPoint.setPointDetailAddress(postPointDto.getPointDetailAddress());
        postPoint.setPointNumber(postPointDto.getPointNumber());
        postPoint.setPointEmail(postPointDto.getPointEmail());
        postPoint.setPointPhone(postPointDto.getPointPhone());
        postPoint.setPointType(postPointDto.getPointType());
        return postPointRepository.save(postPoint);
    }

    public void deletePostPoint(Long id) {
        postPointRepository.findById(id).orElseThrow(() -> AppException.notFound("Post Point was not found!"));
        postPointRepository.deleteById(id);
    }


    public PostPoint setPointLeader(Long pointLeaderId, Long postPointId){
        PostPoint postPoint = postPointRepository.findById(postPointId).orElseThrow(() -> AppException.notFound("Post Point was not found!"));
        if(userRepository.existsById(pointLeaderId)) {
            postPoint.setPointLeaderId(pointLeaderId);
            return postPointRepository.save(postPoint);
        } else {
            throw AppException.notFound("User was not found!");
        }
    }

    public PostPointPageResponse getAllPostPointsWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PostPoint> postPointPages = postPointRepository.findAll(pageable);
        List<PostPoint> postPoints = postPointPages.getContent();
        return new PostPointPageResponse(postPoints, pageNumber, pageSize, (int) postPointPages.getTotalElements(),postPointPages.getTotalPages(), postPointPages.isLast());
    }

    public PostPointPageResponse getAllPostPointsWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<PostPoint> postPointPages = postPointRepository.findAll(pageable);
        List<PostPoint> postPoints = postPointPages.getContent();
        return new PostPointPageResponse(postPoints, pageNumber, pageSize, (int) postPointPages.getTotalElements(),postPointPages.getTotalPages(),postPointPages.isLast());
    }

}
