package com.codewithmosh.store.product;

import com.codewithmosh.store.product.dtos.ProductDto;
import com.codewithmosh.store.product.dtos.UpdateProductRequest;
import com.codewithmosh.store.category.Category;
import com.codewithmosh.store.category.CategoryRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
@Tag(name = "Products")
public class ProductController {

    private final CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ProductMapper productMapper;


    @GetMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(name = "categoryId", required = false) Byte categoryId){
        List<Product> products;


        if(categoryId != null){
            products = productRepository.findByCategoryId(categoryId);
        }
        else{
            products = productRepository.findAllWithCategory();
        }

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId){
       Product product = productRepository.findById(productId).orElse(null);

       if(product == null){
           return ResponseEntity.notFound().build();
       }

       ProductDto newProduct = productMapper.toDto(product);

       return ResponseEntity.ok(newProduct);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(
            @RequestBody ProductDto request,
            UriComponentsBuilder uriBuilder) {

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElse(null);

        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        Product product = productMapper.toEntity(request);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        URI location = uriBuilder
                .path("/products/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody UpdateProductRequest request) {

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        productMapper.update(request, product);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow();

            product.setCategory(category);
        }

        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId){
        Product product = productRepository.findById(productId).orElse(null);

        if(product == null){
            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(productId);

        return ResponseEntity.noContent().build();
    }

}
