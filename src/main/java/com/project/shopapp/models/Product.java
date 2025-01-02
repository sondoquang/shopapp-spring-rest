package com.project.shopapp.models;


import jakarta.persistence.*;
import lombok.*;


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

}

