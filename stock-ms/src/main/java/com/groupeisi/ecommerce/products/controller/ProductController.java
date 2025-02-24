package com.groupeisi.ecommerce.products.controller;

import com.groupeisi.ecommerce.products.dto.requests.ProductDtoRequest;
import com.groupeisi.ecommerce.products.dto.responses.ProductDtoResponse;
import com.groupeisi.ecommerce.products.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Getter
@Setter
@CrossOrigin (value = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDtoResponse> saveProduct(@RequestBody @Valid ProductDtoRequest productDto){
        Optional<ProductDtoResponse> productDto1 = productService.saveProduct(productDto);

        return new ResponseEntity<>(productDto1.get(), HttpStatus.CREATED);
    }
    @PutMapping("/{ref}")
    public ResponseEntity<ProductDtoResponse> saveProduct(@PathVariable("ref") String ref, @RequestBody @Valid ProductDtoRequest productDto){
        Optional<ProductDtoResponse> productDto1 = productService.updateProduct(ref, productDto);

        return new ResponseEntity<>(productDto1.get(), HttpStatus.CREATED);
    }

    @GetMapping("/{ref}")
    public ResponseEntity<ProductDtoResponse> getProduct(@PathVariable("ref") String ref){
        Optional<ProductDtoResponse> productDto1 = productService.getProductByRef(ref);
        return new ResponseEntity<>(productDto1.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDtoResponse>> allProducts(){
        List<ProductDtoResponse> productDtos = productService.getAllProducts();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
 }
