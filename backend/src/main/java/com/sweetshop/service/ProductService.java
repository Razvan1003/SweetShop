package com.sweetshop.service;

import com.sweetshop.dto.ProductRequest;
import com.sweetshop.dto.ProductResponse;
import com.sweetshop.entity.Category;
import com.sweetshop.entity.Product;
import com.sweetshop.exception.ResourceNotFoundException;
import com.sweetshop.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return toResponse(product);
    }

    public List<ProductResponse> search(String query) {
        return search(query, null);
    }

    public List<ProductResponse> search(String query, Long categoryId) {
        List<ProductResponse> base = categoryId == null ? getAll() : getByCategory(categoryId);
        if (query == null || query.isBlank()) {
            return base;
        }
        String normalized = query.toLowerCase();
        return base.stream()
            .filter(product ->
                (product.getName() != null && product.getName().toLowerCase().contains(normalized))
                    || (product.getDescription() != null
                    && product.getDescription().toLowerCase().contains(normalized))
            )
            .collect(Collectors.toList());
    }

    public List<ProductResponse> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public ProductResponse create(ProductRequest request) {
        Category category = categoryService.getEntity(request.getCategoryId());
        Product product = new Product(request.getName(), request.getDescription(), request.getPrice(), category);
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Category category = categoryService.getEntity(request.getCategoryId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private ProductResponse toResponse(Product product) {
        Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            categoryId,
            categoryName
        );
    }
}
