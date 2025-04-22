package com.regi.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private Integer quantity;
    private Float price;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false,unique = true)
    private String imageUrl;
}
