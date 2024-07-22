package com.musinsa.codi.api.item;

import com.musinsa.codi.api.brand.Brand;
import com.musinsa.codi.api.brand.BrandRepository;
import com.musinsa.codi.api.item.dto.ItemSaveRequestDto;
import com.musinsa.codi.api.item.dto.ItemUpdateRequestDto;
import com.musinsa.codi.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final BrandRepository brandRepository;

    private Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품을 찾을 수 없습니다."));
    }

    // 상품 조회 ALL
    public List<Item> findItemAll() {
        return itemRepository.findAll();
    }

    // ID로 상품 검색
    public Item findItem(Long itemId) {
        return findById(itemId);
    }

    // 상품 저장
    public Item saveItem(ItemSaveRequestDto requestDto) {
        // 브랜드 없을 시, EXCEPTION
        Brand brand = brandRepository.findById(requestDto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("해당 브랜드를 찾을 수 없습니다."));

        // 저장
        return itemRepository.save(Item.create(requestDto, brand));
    }

    // 상품 수정
    public void updateItem(ItemUpdateRequestDto requestDto) {
        Brand brand = brandRepository.findById(requestDto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("해당 브랜드를 찾을 수 없습니다."));

        Item item = findById(requestDto.getItemId());

        item.updateItem(requestDto, brand);
    }

    // 상품 삭제
    public void deleteItem(Long itemId) {
        Item findEntity = findById(itemId);

        itemRepository.delete(findEntity);
    }
}
