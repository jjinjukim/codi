package com.musinsa.codi.api.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.codi.common.utils.CommonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
@Schema(title = "상품 가격 및 브랜드 응답 DTO")
public class BrandPriceResponseDto {
    @JsonProperty("브랜드")
    @Schema(description = "브랜드명", example = "FREI")
    private String brandName;

    @JsonProperty("가격")
    @Schema(description = "가격", example = "10,000")
    private String price;

    public static BrandPriceResponseDto of(String brandName, Integer price) {
        BrandPriceResponseDto dto = new BrandPriceResponseDto();

        dto.brandName = brandName;
        dto.price = CommonUtils.formatPrice(price);

        return dto;
    }
}
