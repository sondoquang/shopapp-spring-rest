package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @Column(name="fullname", length=100)
    private String fullName;

    @Column(name="email", length=100)
    private String email;

    @Column(name="phone_number", length=10, nullable = false)
    private String phoneNumber;

    @Column(name="address", length=100)
    private String address;

    @Column(name="note", length=100)
    private String note;

    @Column(name="order_date")
    private Date orderDate;

    private String status;

    @Column(name="total_money", nullable = true)
    private Double totalMoney;

    @Column(name="shipping_method", length = 100)
    private String shippingMethod;

    @Column(name="shipping_address", length = 200)
    private String shippingAddress;

    @Column(name="shipping_date")
    private Date shippingDate;

    @Column(name="active")
    private boolean active;

}
