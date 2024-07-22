package com.musinsa.codi.api.item;

import com.musinsa.codi.api.brand.Brand;
import com.musinsa.codi.api.brand.CategoryId;
import com.musinsa.codi.api.item.dto.ItemSaveRequestDto;
import com.musinsa.codi.api.item.dto.ItemUpdateRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "category_id")
    @Enumerated(EnumType.STRING)
    private CategoryId categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "fk_brand_to_item"))
    private Brand brand;

    @NotNull
    private Integer price;

    public static Item create(ItemSaveRequestDto requestDto, Brand brand) {
        Item item = new Item();

        item.categoryId = requestDto.getCategoryId();
        item.brand = brand;
        item.price = requestDto.getPrice();

        return item;
    }

    public void updateItem(ItemUpdateRequestDto requestDto, Brand brand) {
        this.categoryId = requestDto.getCategoryId();
        this.brand = brand;
        this.price = requestDto.getPrice();
    }
}
