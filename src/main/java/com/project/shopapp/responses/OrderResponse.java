package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse extends BaseResponse {

    private Long userId;
    private String fullName;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String address;
    private String note;
    @JsonProperty("order_date")
    private Date orderDate;
    @JsonProperty("total_money")
    private Double totalMoney;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("shipping_date")
    private Date shippingDate;

}
