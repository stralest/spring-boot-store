package com.codewithmosh.store.product;

import com.codewithmosh.store.product.dtos.ProductDto;
import com.codewithmosh.store.product.dtos.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDto request);

    @Mapping(target = "id", ignore = true)
    void update(UpdateProductRequest request, @MappingTarget Product product);
}
