package com.musinsa.codi.api.search;

import com.musinsa.codi.api.brand.Brand;
import com.musinsa.codi.api.brand.BrandRepository;
import com.musinsa.codi.api.brand.CategoryId;
import com.musinsa.codi.api.item.Item;
import com.musinsa.codi.api.item.ItemRepository;
import com.musinsa.codi.api.search.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {
    private final ItemRepository itemRepository;
    private final BrandRepository brandRepository;

    public List<LowestPriceCategoryResponseDto> getLowestPriceByCategoryId() {
        return Arrays.stream(CategoryId.values())
                .flatMap(category ->
                        // 카테고리로 상품 검색
                        // 최저가 찾는다.
                        itemRepository.findByCategoryId(category).stream()
                                .min(Comparator.comparingInt(Item::getPrice))
                                .map(item -> {
                                    Brand brand = item.getBrand();
                                    String categoryName = category.getName();
                                    String brandName = brand.getName();
                                    int lowestPrice = item.getPrice();

                                    return LowestPriceCategoryResponseDto.of(categoryName, brandName, lowestPrice);
                                })
                                .stream()
                )
                .collect(Collectors.toList());
    }

    // 1. 모든 브랜드를 조회한다.
    // 2. 각 브랜드가 모든 상품 카테고리를 포함하는지 확인
    // 3. 최저가 브랜드 찾는다.
    // 4. 총요금 계산
   public LowestPriceResponseDto getLowestPriceByBrand() {
        LowestPriceBrandResponseDto lowestPriceBrandResponseDto = brandRepository.findAll().stream()
                .filter(brand -> {
                   // 카테고리 8개가 모두 존재하는 상품만 필터링한다.
                   List<Item> findItems = itemRepository.findByBrand(brand).stream()
                           .distinct()
                           .collect(Collectors.toList());

                   return findItems.size() == 8;
               })
                .map(this::getLowestPriceForBrand)
                .min(Comparator.comparingInt(dto -> Integer.valueOf(dto.getTotalPrice().replaceAll(",", ""))))
                .orElse(null);

        return LowestPriceResponseDto.from(lowestPriceBrandResponseDto);
    }

    public ItemPriceResponseDto getMinMaxPriceByCategory(CategoryId categoryId) {
        List<Item> itemList = itemRepository.findByCategoryId(categoryId);

        // 최저가 상품
        Item minItem = itemList.stream()
                .min(Comparator.comparing(Item::getPrice))
                .get();

        // 최고가 상품
        Item maxItem = itemList.stream()
                .max(Comparator.comparing(Item::getPrice))
                .get();

        // dto 변환
        return ItemPriceResponseDto.of(categoryId,
                BrandPriceResponseDto.of(minItem.getBrand().getName(), minItem.getPrice()),
                BrandPriceResponseDto.of(maxItem.getBrand().getName(), maxItem.getPrice()));
    }

    // 브랜드별 최저가 추출
    // 합산 요금 계산
    private LowestPriceBrandResponseDto getLowestPriceForBrand(Brand brand) {
        List<Item> items = itemRepository.findByBrand(brand);

        // 카테고리명 : 가격
        Map<String, CategoryResponseDto> minPriceItemsMap = items.stream()
                .collect(Collectors.groupingBy(
                        item -> item.getCategoryId().getName(),
                        Collectors.collectingAndThen(
                                Collectors.minBy(Comparator.comparingInt(Item::getPrice)),
                                itemOptional -> itemOptional.map(item -> CategoryResponseDto.of(
                                        item.getCategoryId().getName(),
                                        item.getPrice(),
                                        item.getCategoryId().getOrder()
                                )).orElse(null)
                        )
                ));

        List<CategoryResponseDto> lowestPrices = minPriceItemsMap.values().stream()
                .filter(dto -> dto != null)
                .sorted(Comparator.comparingInt(CategoryResponseDto::getSortNumber))
                .collect(Collectors.toList());

        int totalPrice = items.stream()
                .mapToInt(Item::getPrice)
                .sum();

        return LowestPriceBrandResponseDto.of(
                brand.getName(),
                lowestPrices,
                totalPrice
        );
    }
}
