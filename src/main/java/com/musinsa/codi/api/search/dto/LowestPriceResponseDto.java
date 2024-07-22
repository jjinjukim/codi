package com.musinsa.codi.api.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LowestPriceResponseDto {
    @JsonProperty("최저가")
    private LowestPriceBrandResponseDto lowestPrice;

    public static LowestPriceResponseDto from(LowestPriceBrandResponseDto lowestPrice) {
        LowestPriceResponseDto dto = new LowestPriceResponseDto();

        dto.lowestPrice = lowestPrice;

        return dto;
    }
}
