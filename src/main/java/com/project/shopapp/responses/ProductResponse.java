package com.project.shopapp.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends BaseResponse {
    private String name;
    private Double price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
}
