package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;


public class OrderDetailDTO {

    @JsonProperty("order_id")
    @Min(value=1, message = "Order's ID must be > 1 !")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value=1, message = "Product's ID must be > 1 !")
    private Long productId;

    @Min(value=0, message = "Product's price must be >= 0 !")
    private Long price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value=0, message = "Product's total money must be >= 0 !")
    private int totalMoney;

    private String color;

    public OrderDetailDTO() {}

    public OrderDetailDTO(Long orderId, Long productId, Long price, int numberOfProducts, int totalMoney, String color) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.numberOfProducts = numberOfProducts;
        this.totalMoney = totalMoney;
        this.color = color;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", price=" + price +
                ", numberOfProducts=" + numberOfProducts +
                ", totalMoney=" + totalMoney +
                ", color='" + color + '\'' +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
