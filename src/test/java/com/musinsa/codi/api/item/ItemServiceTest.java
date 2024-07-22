package com.musinsa.codi.api.item;

import com.musinsa.codi.api.brand.Brand;
import com.musinsa.codi.api.brand.BrandService;
import com.musinsa.codi.api.brand.CategoryId;
import com.musinsa.codi.api.item.dto.ItemSaveRequestDto;
import com.musinsa.codi.api.item.dto.ItemUpdateRequestDto;
import com.musinsa.codi.common.exception.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    BrandService brandService;

    @Test
    @DisplayName("상품 저장")
    void saveItem() {
        ItemSaveRequestDto requestDto = new ItemSaveRequestDto();

        requestDto.setBrandId(100L);
        requestDto.setCategoryId(CategoryId.BAG);
        requestDto.setPrice(50000);

        // 존재하지 않는 브랜드 ID -> exception 발생
        Assertions.assertThatThrownBy(() -> itemService.saveItem(requestDto)).isInstanceOf(EntityNotFoundException.class);

        // 특정 브랜드를 찾는다.
        Brand findBrand = brandService.findBrandAll().get(0);

        requestDto.setBrandId(findBrand.getId());

        Item savedItem = itemService.saveItem(requestDto);

        // 같은 상품인지 확인
        Assertions.assertThat(savedItem.getPrice()).isEqualTo(50000);
    }

    @Test
    @DisplayName("상품 수정")
    void updateItem() {
        Item findItem = itemService.findItemAll().get(0);

        Integer price = findItem.getPrice();

        ItemUpdateRequestDto requestDto = new ItemUpdateRequestDto();
        requestDto.setBrandId(findItem.getBrand().getId());
        requestDto.setItemId(findItem.getId());
        requestDto.setCategoryId(findItem.getCategoryId());
        requestDto.setPrice(price + 1000);

        itemService.updateItem(requestDto);

        // 요금이 변경됐는지 확인
        Assertions.assertThat(findItem.getPrice()).isEqualTo(price + 1000);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void findAllItem() {
        // 상품 총 72개
        Assertions.assertThat(itemService.findItemAll().size()).isEqualTo(72);
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteItem() {
        Item findItem = itemService.findItemAll().get(0);

        Long brandId = findItem.getBrand().getId();

        // 상품 삭제
        itemService.deleteItem(findItem.getId());

        // 재조회 시 exception 발생
        Assertions.assertThatThrownBy(
                () -> itemService.findItem(findItem.getId())).isInstanceOf(EntityNotFoundException.class);
    }


}
