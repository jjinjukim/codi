package com.musinsa.codi.api.brand;

import com.musinsa.codi.api.brand.dto.BrandSaveRequestDto;
import com.musinsa.codi.api.brand.dto.BrandUpdateRequestDto;
import com.musinsa.codi.api.item.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name="uk_name", columnNames = "name")
})
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    public static Brand create(BrandSaveRequestDto requestDto) {
        Brand brand = new Brand();

        brand.name = requestDto.getBrandName();

        return brand;
    }

    public void updateBrand(BrandUpdateRequestDto requestDto) {
        this.name = requestDto.getBrandName();
    }
}
