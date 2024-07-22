package com.musinsa.codi.api.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.codi.common.utils.CommonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(title = "카테고리별 상품 응답 DTO")
@Getter
public class CategoryResponseDto {
    @JsonProperty("카테고리")
    @Schema(description = "카테고리명", example = "TOP")
    private String categoryName;

    @JsonProperty("가격")
    @Schema(description = "가격", example = "10,000")
    private String price;

    @JsonIgnore
    private Integer sortNumber;

    public static CategoryResponseDto of(String categoryName, Integer price, Integer sortNumber) {
        CategoryResponseDto dto = new CategoryResponseDto();

        dto.categoryName = categoryName;
        dto.price = CommonUtils.formatPrice(price);
        dto.sortNumber = sortNumber;

        return dto;
    }
}
