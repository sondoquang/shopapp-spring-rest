package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;


@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    @JsonProperty("order_id")
    @Min(value=1, message = "Order's ID must be > 1 !")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value=1, message = "Product's ID must be > 1 !")
    private Long productId;

    @Min(value=0, message = "Product's price must be >= 0 !")
    private Double price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value=0, message = "Product's total money must be >= 0 !")
    private Double totalMoney;

    private String color;

}
