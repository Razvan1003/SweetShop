package com.sweetshop.service;

import com.sweetshop.dto.CategoryRequest;
import com.sweetshop.dto.CategoryResponse;
import com.sweetshop.entity.Category;
import com.sweetshop.exception.ResourceNotFoundException;
import com.sweetshop.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
            .map(category -> new CategoryResponse(category.getId(), category.getName()))
            .collect(Collectors.toList());
    }

    public CategoryResponse create(CategoryRequest request) {
        Category category = new Category(request.getName());
        Category saved = categoryRepository.save(category);
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    public Category getEntity(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
