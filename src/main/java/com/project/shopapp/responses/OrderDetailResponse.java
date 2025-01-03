package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;
    private Double price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private Double totalMoney;
    private String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();
    }
}
