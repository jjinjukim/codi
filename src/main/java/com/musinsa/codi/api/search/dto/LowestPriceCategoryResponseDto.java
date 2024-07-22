package com.musinsa.codi.api.search.dto;

import com.musinsa.codi.common.utils.CommonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LowestPriceCategoryResponseDto {
    @Schema(description = "상품 카테고리", example = "TOP")
    private String categoryId;

    @Schema(description = "브랜드명", example = "FREI")
    private String brandName;

    @Schema(description = "상품 가격", example = "10,000")
    private String price;

    public static LowestPriceCategoryResponseDto of(String categoryId, String brandName, Integer price) {
        LowestPriceCategoryResponseDto dto = new LowestPriceCategoryResponseDto();

        dto.categoryId = categoryId;
        dto.brandName = brandName;
        dto.price = CommonUtils.formatPrice(price);

        return dto;
    }
}
