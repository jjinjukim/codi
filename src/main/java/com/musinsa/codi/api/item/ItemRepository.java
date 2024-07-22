package com.musinsa.codi.api.item;

import com.musinsa.codi.api.brand.Brand;
import com.musinsa.codi.api.brand.CategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryId(CategoryId categoryId);

    List<Item> findByBrand(Brand brand);
}
