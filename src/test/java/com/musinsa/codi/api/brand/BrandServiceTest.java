package com.musinsa.codi.api.brand;

import com.musinsa.codi.api.brand.dto.BrandSaveRequestDto;
import com.musinsa.codi.api.brand.dto.BrandUpdateRequestDto;
import com.musinsa.codi.api.item.ItemRepository;
import com.musinsa.codi.common.exception.DuplicationDataException;
import com.musinsa.codi.common.exception.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class BrandServiceTest {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandService brandService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("브랜드 저장")
    void saveBrand() {
        BrandSaveRequestDto requestDto = new BrandSaveRequestDto();

        requestDto.setBrandName("J");
        Brand savedBrand = brandService.saveBrand(requestDto);

        // 같은 브랜드인지 확인
        Assertions.assertThat(savedBrand.getName()).isEqualTo("J");
    }

    @Test
    @DisplayName("같은 브랜드 저장")
    void duplicationDataSave() {
        Brand brand = brandService.findBrandAll().get(0);

        BrandSaveRequestDto requestDto = new BrandSaveRequestDto();

        requestDto.setBrandName(brand.getName());

        // 중복키 EX를 던지는지 확인
        Assertions.assertThatThrownBy(() -> brandService.saveBrand(requestDto)).isInstanceOf(DuplicationDataException.class);
    }

    @Test
    @DisplayName("브랜드 수정")
    void updateBrand() {
        Brand findBrand = brandRepository.findByName("A")
                .orElseThrow(() -> new EntityNotFoundException("브랜드를 찾을 수 없습니다."));

        BrandUpdateRequestDto requestDto = new BrandUpdateRequestDto();
        requestDto.setBrandId(findBrand.getId());
        requestDto.setBrandName("K");

        brandService.updateBrand(requestDto);

        Assertions.assertThat(findBrand.getName()).isEqualTo("K");
    }

    @Test
    @DisplayName("브랜드 전체 조회")
    void findAllBrand() {
        // 브랜드는 총 9개
        Assertions.assertThat(brandService.findBrandAll().size()).isEqualTo(9);
    }

    @Test
    @DisplayName("브랜드 삭제")
    void deleteBrand() {
        Brand findBrand = brandService.findBrandAll().get(0);

        // 브랜드 삭제
        brandService.deleteBrand(findBrand.getId());

        // 재조회 시 exception 발생
        Assertions.assertThatThrownBy(
                () -> brandService.findBrand(findBrand.getId())).isInstanceOf(EntityNotFoundException.class);

        // 고아객체(Item) 삭제 여부 확인
        // 해당 브랜드로 찾은 상품의 size가 0이여야한다.
        Assertions.assertThat(itemRepository.findByBrand(findBrand).size()).isEqualTo(0);
    }
}