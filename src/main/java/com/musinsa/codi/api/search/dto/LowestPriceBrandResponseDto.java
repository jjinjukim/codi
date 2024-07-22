package com.musinsa.codi.api.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.codi.common.utils.CommonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class LowestPriceBrandResponseDto {
    @JsonProperty("브랜드")
    @Schema(description = "브랜드명", example = "FREI")
    private String brandName;

    @JsonProperty("카테고리")
    private List<CategoryResponseDto> category;

    @JsonProperty("총액")
    @Schema(description = "총금액", example = "36,100")
    private String totalPrice;

    public static LowestPriceBrandResponseDto of(String brandName, List<CategoryResponseDto> category, Integer totalPrice) {
        LowestPriceBrandResponseDto dto = new LowestPriceBrandResponseDto();

        dto.brandName = brandName;
        dto.category = category;
        dto.totalPrice = CommonUtils.formatPrice(totalPrice);

        return dto;
    }
}
