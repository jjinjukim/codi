package com.musinsa.codi.api.item;

import com.musinsa.codi.api.brand.dto.BrandResponseDto;
import com.musinsa.codi.api.item.dto.ItemResponseDto;
import com.musinsa.codi.api.item.dto.ItemSaveRequestDto;
import com.musinsa.codi.api.item.dto.ItemUpdateRequestDto;
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

@Tag(name = "ITEM REST API", description = "상품 API")
@RestController
@RequestMapping("/musinsa/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    @Operation(summary = "상품 조회", description = "모든 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품이 조회되었습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }")))
    })
    public List<ItemResponseDto> findItemAll() {
        List<Item> items = itemService.findItemAll();

        // entity to dto
        return items.stream().map(item -> {
                    return ItemResponseDto.builder()
                            .id(item.getId())
                            .category(item.getCategoryId().getName())
                            .brand(BrandResponseDto.builder()
                                    .brandName(item.getBrand().getName())
                                    .id(item.getBrand().getId())
                                    .build())
                            .price(item.getPrice())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "상품 조회", description = "특정 상품을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품이 조회되었습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"유효한 ID가 아닙니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }")))
    })
    public ItemResponseDto findItem(@PathVariable Long itemId) {
        Item item = itemService.findItem(itemId);

        // entity to dto
        return ItemResponseDto.builder()
                .id(item.getId())
                .category(item.getCategoryId().getName())
                .brand(BrandResponseDto.builder()
                        .brandName(item.getBrand().getName())
                        .id(item.getBrand().getId())
                        .build())
                .price(item.getPrice())
                .build();
    }

    // 아이템 저장
    @Operation(summary = "상품 추가", description = "새로운 상품을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "새로운 상품이 추가되었습니다.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "상품 저장 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"입력값 검증에 실패했습니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PostMapping
    public ResponseEntity<Long> saveItem(@Valid @RequestBody ItemSaveRequestDto saveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.saveItem(saveRequestDto).getId());
    }

    @Operation(summary = "상품 수정", description = "상품 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 정보가 수정되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "상품 정보 수정 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"입력값 검증에 실패했습니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateItem(@Valid @RequestBody ItemUpdateRequestDto updateRequestDto) {
        itemService.updateItem(updateRequestDto);
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "상품이 삭제되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "상품 삭제 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"유효하지 않은 ID 입니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }
}
