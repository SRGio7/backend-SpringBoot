package com.regi.backend.controller;

import com.regi.backend.dto.ProductDTO;
import com.regi.backend.entity.Product;
import com.regi.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDTO create(@RequestBody Product product) {
        Product createdProduct = productService.create(product);
        return convertToDTO(createdProduct);
    }

    @GetMapping
    public List<ProductDTO> getListProducts() {
        List<Product> products = productService.getListData();
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getDetailProduct(@PathVariable("id") Long id) {
        Product product = productService.getDataDetail(id);
        return convertToDTO(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleted(id);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable("id") Long id, @RequestBody Product product) {
        Product updated = productService.update(id, product);
        return convertToDTO(updated);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        productDTO.setImageUrl(product.getImageUrl());
        return productDTO;
    }
}
