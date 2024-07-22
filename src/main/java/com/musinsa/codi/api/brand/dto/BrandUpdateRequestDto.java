package com.musinsa.codi.api.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Schema(title = "브랜드 update 요청 DTO")
@Setter
public class BrandUpdateRequestDto {
    @NotNull(message = "브랜드 ID를 입력하세요.")
    @Schema(description = "브랜드 아이디")
    private Long brandId;

    @NotBlank(message = "브랜드명을 입력하세요.")
    @Schema(description = "브랜드명", example = "FREI")
    private String brandName;
}
