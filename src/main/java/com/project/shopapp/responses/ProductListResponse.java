package com.project.shopapp.responses;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {

    private List<ProductResponse> products;
    private int totalPage;

}
