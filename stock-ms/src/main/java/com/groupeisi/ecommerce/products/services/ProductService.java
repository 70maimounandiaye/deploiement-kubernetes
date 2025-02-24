package com.groupeisi.ecommerce.products.services;

import com.groupeisi.ecommerce.products.dto.requests.ProductDtoRequest;
import com.groupeisi.ecommerce.products.dto.responses.ProductDtoResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDtoResponse> saveProduct(ProductDtoRequest productDto);
    List<ProductDtoResponse> getAllProducts();
    Optional<ProductDtoResponse> getProductByRef(String ref);
    boolean deleteProduct(String ref);
    Optional<ProductDtoResponse> updateProduct(String ref, ProductDtoRequest productDto);

}
