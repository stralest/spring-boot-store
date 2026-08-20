package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product createProduct(ProductDto request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());

        return productRepository.save(product);
    }

    @Transactional
    public void assignCategoryToProduct(Long productId, Byte categoryId){
        Product product = productRepository.findById(productId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        product.addCategory(category);
    }
}
