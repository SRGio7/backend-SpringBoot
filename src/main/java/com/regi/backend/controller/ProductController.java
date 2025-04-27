package com.regi.backend.controller;

import com.regi.backend.dto.ProductDTO;
import com.regi.backend.entity.Product;
import com.regi.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Value("${upload.path:src/main/resources/static/assets}")
    private String uploadDir;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ProductDTO create(
            @RequestParam("name") String name,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") float price,
            @RequestParam("category") String category,
            @RequestParam("image_url") MultipartFile imageFile
    ) throws IOException {

        // Buat direktori uploads jika belum ada
        Path uploadsDir = Paths.get("uploads");
        if (!Files.exists(uploadsDir)) {
            Files.createDirectories(uploadsDir);
        }

        String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
        Path filePath = uploadsDir.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Simpan URL relatif untuk diakses melalui ImageController
        String imageUrl = "/images/" + fileName;

        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setCategory(category);
        product.setImageUrl(imageUrl);

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
