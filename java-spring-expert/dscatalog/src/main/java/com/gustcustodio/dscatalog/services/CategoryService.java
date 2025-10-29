package com.gustcustodio.dscatalog.services;

import com.gustcustodio.dscatalog.dtos.CategoryDTO;
import com.gustcustodio.dscatalog.entities.Category;
import com.gustcustodio.dscatalog.repositories.CategoryRepository;
import com.gustcustodio.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        return new CategoryDTO(category);
    }

}
