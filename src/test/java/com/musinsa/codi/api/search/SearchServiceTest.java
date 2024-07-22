package com.musinsa.codi.api.search;

import com.musinsa.codi.api.brand.Brand;
import com.musinsa.codi.api.brand.BrandRepository;
import com.musinsa.codi.api.brand.CategoryId;
import com.musinsa.codi.api.item.ItemRepository;
import com.musinsa.codi.api.item.Item;
import com.musinsa.codi.api.search.dto.CategoryResponseDto;
import com.musinsa.codi.api.search.dto.ItemPriceResponseDto;
import com.musinsa.codi.api.search.dto.LowestPriceResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional(readOnly = true)
public class SearchServiceTest {
    @Autowired
    SearchService searchService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    BrandRepository brandRepository;

    @Test
    @DisplayName("카테고리별 최저가격 브랜드 총액 비교")
    void getLowestPriceBycategoryId() {
        int sum = Arrays.stream(CategoryId.values())
                .mapToInt(categoryId -> {
                    return itemRepository.findByCategoryId(categoryId).stream()
                            .min(Comparator.comparingInt(Item::getPrice))
                            .get().getPrice();
                })
                .sum();

        int total = searchService.getLowestPriceByCategoryId().stream()
                .mapToInt(dto -> Integer.valueOf(dto.getPrice().replaceAll(",", "")))
                .sum();

        Assertions.assertThat(sum).isEqualTo(total);
    }

    @Test
    @DisplayName("최저가격에 판매하는 브랜드와 카테고리의 총액 비교")
    void getLowestPriceByBrand() {
        // 총액비교
        Integer total = brandRepository.findAll().stream()
                .map(this::getLowestPriceForBrand)
                .min(Comparator.comparingInt(value -> value))
                .orElse(null);

        LowestPriceResponseDto dto = searchService.getLowestPriceByBrand();

        Integer sum = Integer.valueOf(dto.getLowestPrice().getTotalPrice().replaceAll(",", ""));

        Assertions.assertThat(sum).isEqualTo(total);
    }

    @Test
    @DisplayName("최저/최고가 브랜드 및 가격 비교")
    void getMinMaxPriceByCategory() {
        List<Item> itemList = itemRepository.findByCategoryId(CategoryId.TOP);

        // 최저가
        Integer minPrice = itemList.stream()
                .min(Comparator.comparing(Item::getPrice))
                .get()
                .getPrice();

        // 최고가
        Integer maxPrice = itemList.stream()
                .max(Comparator.comparing(Item::getPrice))
                .get()
                .getPrice();

        ItemPriceResponseDto dto = searchService.getMinMaxPriceByCategory(CategoryId.TOP);

        Integer max = Integer.valueOf(dto.getHighestPriceDto().getPrice().replaceAll(",",""));
        Integer min = Integer.valueOf(dto.getLowestPriceDto().getPrice().replaceAll(",", ""));

        Assertions.assertThat(maxPrice).isEqualTo(max);
        Assertions.assertThat(minPrice).isEqualTo(min);
    }

    private int getLowestPriceForBrand(Brand brand) {
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

        int totalPrice = items.stream()
                .mapToInt(Item::getPrice)
                .sum();

        return totalPrice;
    }
}
