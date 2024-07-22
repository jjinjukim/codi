package com.musinsa.codi.api.item.dto;

import com.musinsa.codi.api.brand.CategoryId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Schema(title = "상품 update 요청 DTO")
@Setter
public class ItemUpdateRequestDto {
    @NotNull(message = "상품 ID를 입력하세요.")
    private Long itemId;

    @NotNull(message = "카테고리를 지정하세요.")
    @Schema(description = "상품 카테고리", example = "TOP")
    private CategoryId categoryId;

    @NotNull(message = "브랜드 ID를 입력하세요.")
    @Schema(description = "브랜드 아이디")
    private Long brandId;

    @NotNull(message = "가격을 입력하세요.")
    @Schema(description = "상품 가격", example = "10,000")
    private Integer price;
}
