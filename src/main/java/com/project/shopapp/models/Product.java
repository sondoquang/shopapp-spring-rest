package com.project.shopapp.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable=false, length = 350)
    private String name;

    private Double price;

    @Column(name="thumbnail", length = 300)
    private String thumbnail;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;


    public Product( String name, Double price, String thumbnail, String description, Category category, LocalDate createdAt, LocalDate updateAt) {
        super(createdAt, updateAt);
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.description = description;
        this.category = category;
    }

    public Product( String name, Double price, String thumbnail, String description, Category category) {
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.description = description;
        this.category = category;
    }
}

