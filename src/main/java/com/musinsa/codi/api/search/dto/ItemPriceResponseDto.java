package com.musinsa.codi.api.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.codi.api.brand.CategoryId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "상품 최저가/최고가 응답 DTO")
public class ItemPriceResponseDto {
    @JsonProperty("카테고리")
    @Schema(description = "카테고리명", example = "TOP")
    private String categoryId;

    @JsonProperty("최저가")
    private BrandPriceResponseDto lowestPriceDto;

    @JsonProperty("최고가")
    private BrandPriceResponseDto highestPriceDto;

    public static ItemPriceResponseDto of(CategoryId categoryId,
                                          BrandPriceResponseDto lowestPriceDto,
                                          BrandPriceResponseDto highestPriceDto) {
        ItemPriceResponseDto dto = new ItemPriceResponseDto();

        dto.categoryId = categoryId.getName();
        dto.lowestPriceDto = lowestPriceDto;
        dto.highestPriceDto = highestPriceDto;

        return dto;
    }
}
