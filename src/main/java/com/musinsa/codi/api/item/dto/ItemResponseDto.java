package com.musinsa.codi.api.item.dto;

import com.musinsa.codi.api.brand.dto.BrandResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ItemResponseDto {
    //    @JsonIgnore
    @Schema(description = "품목 ID", example = "TOP")
    private Long id;

    @Schema(description = "카테고리", example = "상의")
    private String category;

    @Schema(description = "브랜드")
    private BrandResponseDto brand;

    @Schema(description = "가격", example = "10,000")
    private Integer price;
}
