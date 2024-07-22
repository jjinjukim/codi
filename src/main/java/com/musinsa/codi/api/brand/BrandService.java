package com.musinsa.codi.api.brand;

import com.musinsa.codi.api.brand.dto.BrandSaveRequestDto;
import com.musinsa.codi.api.brand.dto.BrandUpdateRequestDto;
import com.musinsa.codi.common.exception.DuplicationDataException;
import com.musinsa.codi.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;

    private Brand findById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 브랜드를 찾을 수 없습니다."));
    }

    // 특정 브랜드 검색
    public Brand findBrand(Long brandId) {
        return findById(brandId);
    }

    // 브랜드 조회 ALL
    public List<Brand> findBrandAll() {
        return brandRepository.findAll();
    }

    public Brand saveBrand(BrandSaveRequestDto requestDto) {
        Brand brand = null;

        // 중복데이터 삽입 시, EXCEPTION
        try {
            brand = brandRepository.save(Brand.create(requestDto));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicationDataException("중복 데이터가 삽입되었습니다.");
        }

        return brand;
    }

    public void updateBrand(BrandUpdateRequestDto requestDto) {
        // 브랜드를 찾는다.
        Brand findEntity = findById(requestDto.getBrandId());

        // 수정
        // 중복데이터 존재 시, EXCEPTION
        try {
            findEntity.updateBrand(requestDto);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicationDataException("중복 데이터가 존재합니다.");
        }
    }

    // 삭제
    public void deleteBrand(Long brandId) {
        Brand findEntity = findById(brandId);

        brandRepository.delete(findEntity);
    }
}
