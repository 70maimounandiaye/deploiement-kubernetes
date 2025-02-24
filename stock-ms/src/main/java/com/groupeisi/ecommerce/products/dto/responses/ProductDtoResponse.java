package com.groupeisi.ecommerce.products.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoResponse {
    private String ref;
    private String name;
    private double stock;
    private long idUser;
}
