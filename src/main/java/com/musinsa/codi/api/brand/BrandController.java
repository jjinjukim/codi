package com.musinsa.codi.api.brand;

import com.musinsa.codi.api.brand.dto.BrandResponseDto;
import com.musinsa.codi.api.brand.dto.BrandSaveRequestDto;
import com.musinsa.codi.api.brand.dto.BrandUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Tag(name = "BRAND REST API", description = "브랜드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/musinsa/api/brand")
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    @Operation(summary = "브랜드 조회", description = "모든 브랜드를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "브랜드가 조회되었습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }")))
    })
    public List<BrandResponseDto> findBrandAll() {
        List<Brand> brands = brandService.findBrandAll();

        // entity to dto
        return brands.stream().map(brand -> {
            return BrandResponseDto.builder()
                    .id(brand.getId())
                    .brandName(brand.getName())
                    .build();
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{brandId}")
    @Operation(summary = "브랜드 조회", description = "특정 브랜드를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "브랜드가 조회되었습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"유효한 ID가 아닙니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }")))
    })
    public BrandResponseDto findBrand(@PathVariable Long brandId) {
        Brand brand = brandService.findBrand(brandId);

        return BrandResponseDto.builder()
                .id(brand.getId())
                .brandName(brand.getName())
                .build();
    }


    // 브랜드 저장
    @Operation(summary = "브랜드 저장", description = "새로운 브랜드를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "새로운 브랜드가 추가되었습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "브랜드 저장 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"입력값 검증에 실패했습니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PostMapping
    public ResponseEntity<Long> saveBrand(@Valid @RequestBody BrandSaveRequestDto saveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.saveBrand(saveRequestDto).getId());
    }

    @Operation(summary = "브랜드 수정", description = "브랜드 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "브랜드 정보가 수정되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "브랜드 정보 수정 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"입력값 검증에 실패했습니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateBrand(@Valid @RequestBody BrandUpdateRequestDto updateRequestDto) {
        brandService.updateBrand(updateRequestDto);
    }

    @Operation(summary = "브랜드 삭제", description = "브랜드를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "브랜드가 삭제되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "브랜드 삭제 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"유효하지 않은 ID 입니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @DeleteMapping("/{brandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
    }
}
