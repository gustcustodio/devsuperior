package com.gustcustodio.dscatalog.services;

import com.gustcustodio.dscatalog.dtos.CategoryDTO;
import com.gustcustodio.dscatalog.dtos.ProductDTO;
import com.gustcustodio.dscatalog.entities.Category;
import com.gustcustodio.dscatalog.entities.Product;
import com.gustcustodio.dscatalog.projections.ProductProjection;
import com.gustcustodio.dscatalog.repositories.CategoryRepository;
import com.gustcustodio.dscatalog.repositories.ProductRepository;
import com.gustcustodio.dscatalog.services.exceptions.DatabaseException;
import com.gustcustodio.dscatalog.services.exceptions.ResourceNotFoundException;
import com.gustcustodio.dscatalog.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return page.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(String name, String categoryId, Pageable pageable) {
        List<Long> categoryIds = Arrays.asList();
        if (!"0".equals(categoryId)) {
            categoryIds = Arrays.asList(categoryId.split(",")).stream().map(Long::parseLong).toList();
        }
        Page<ProductProjection> page = productRepository.searchProducts(categoryIds, name, pageable);
        List<Long> productIds = page.map(product -> product.getId()).toList();
        List<Product> entities = productRepository.searchProductsWithCategories(productIds);
        entities = (List<Product>) Utils.replace(page.getContent(), entities);
        List<ProductDTO> dtos = entities.stream().map(product -> new ProductDTO(product, product.getCategories())).toList();
        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found");
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());
        entity.getCategories().clear();
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            Category category = categoryRepository.getReferenceById(categoryDTO.getId());
            entity.getCategories().add(category);
        }
    }

}
