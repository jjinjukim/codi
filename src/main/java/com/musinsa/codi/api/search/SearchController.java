package com.musinsa.codi.api.search;

import com.musinsa.codi.api.brand.CategoryId;
import com.musinsa.codi.api.search.dto.ItemPriceResponseDto;
import com.musinsa.codi.api.search.dto.LowestPriceCategoryResponseDto;
import com.musinsa.codi.api.search.dto.LowestPriceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "SEARCH REST API", description = "검색 API")
@RequestMapping("/musinsa/api/search")
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "브랜드, 상품 가격, 총액 조회", description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 완료.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LowestPriceCategoryResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @GetMapping("/categoryItem/lowestPrice")
    public List<LowestPriceCategoryResponseDto> getLowestPriceByCategoryId() {
        return searchService.getLowestPriceByCategoryId();
    }

    @Operation(summary = "최저가 브랜드 상품 가격 및 총액 조회",
            description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 완료.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LowestPriceResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @GetMapping("/brand/lowestPrice")
    public LowestPriceResponseDto getLowestPriceByBrand() {
        return searchService.getLowestPriceByBrand();
    }

    @Operation(summary = "카테고리별 최저/최고 가격 상품 조회",
            description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API\n")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 완료.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemPriceResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @GetMapping("/lowestPrice/{categoryId}")
    public ItemPriceResponseDto getMinMaxPriceByCategory(@PathVariable CategoryId categoryId) {
        return searchService.getMinMaxPriceByCategory(categoryId);
    }
}
