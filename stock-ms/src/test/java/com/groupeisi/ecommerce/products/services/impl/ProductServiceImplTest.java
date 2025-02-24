package com.groupeisi.ecommerce.products.services.impl;

import com.groupeisi.ecommerce.exception.EntityExistsException;
import com.groupeisi.ecommerce.exception.EntityNotFoundException;
import com.groupeisi.ecommerce.products.dto.requests.ProductDtoRequest;
import com.groupeisi.ecommerce.products.dto.responses.ProductDtoResponse;
import com.groupeisi.ecommerce.products.entities.ProductEntity;
import com.groupeisi.ecommerce.products.mapper.ProductsMapper;
import com.groupeisi.ecommerce.products.repository.ProductRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductsMapper productsMapper;
    @Mock
    private MessageSource messageSource;


    @Test
    void saveProductOK() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.empty());
        when(productsMapper.toProductEntity(any())).thenReturn(this.getProductEntity());
        when(productRepository.save(any())).thenReturn(this.getProductEntity());
        when(productsMapper.toProductDtoResponse(any())).thenReturn(this.getProductDtoResponse());

        Optional<ProductDtoResponse> productDtoResponse1 = productService.saveProduct(this.getProductDtoRequest());
        assertTrue(productDtoResponse1.isPresent());
    }

    @Test
    void saveProductKO() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.of(this.getProductEntity()));
        when(messageSource.getMessage(eq("product.exists"), any(), any(Locale.class))).thenReturn("the product with ref = MAD01 is already created");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> productService.saveProduct(this.getProductDtoRequest()));
        assertEquals("the product with ref = MAD01 is already created", exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(this.getProductEntity()));
        when(productsMapper.toProductDtoResponseList(any())).thenReturn(List.of(this.getProductDtoResponse()));

        List<ProductDtoResponse> products = productService.getAllProducts();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void getProductByRefOK() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.of(getProductEntity()));
        when(productsMapper.toProductDtoResponse(any())).thenReturn(getProductDtoResponse());

        Optional<ProductDtoResponse> product = productService.getProductByRef("MAD01");
        assertTrue(product.isPresent());
        assertEquals("MAD01", product.get().getRef());
    }

    @Test
    void getProductByRefKO() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("product.notfound"), any(), any(Locale.class))).thenReturn("Product not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.getProductByRef("MAD01"));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void deleteProductOK() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.of(getProductEntity()));

        boolean result = productService.deleteProduct(anyString());
        assertTrue(result);
        verify(productRepository, times(1)).deleteById(anyString());
    }

    @Test
    void deleteProductKO() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("product.notfound"), any(), any(Locale.class)))
                .thenReturn("Product not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct("MAD01"));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void updateProductOK() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.of(getProductEntity()));
        when(productRepository.save(any())).thenReturn(getProductEntity());
        when(productsMapper.toProductDtoResponse(any())).thenReturn(getProductDtoResponse());

        Optional<ProductDtoResponse> updatedProduct = productService.updateProduct("MAD01", getProductDtoRequest());
        assertTrue(updatedProduct.isPresent());
        assertEquals("MAD01", updatedProduct.get().getRef());
    }

    @Test
    void updateProductKO() {
        when(productRepository.findByRef(anyString())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("product.notfound"), any(), any(Locale.class)))
                .thenReturn("Product not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.updateProduct("MAD01", getProductDtoRequest()));
        assertEquals("Product not found", exception.getMessage());
    }

    private ProductDtoRequest getProductDtoRequest(){
        ProductDtoRequest productDtoRequest = new ProductDtoRequest();
        productDtoRequest.setRef("MAD01");
        productDtoRequest.setName("Madar");
        productDtoRequest.setStock(100.0);
        productDtoRequest.setIdUser(1);

        return productDtoRequest;
    }

    private ProductEntity getProductEntity(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setRef("MAD01");
        productEntity.setName("Madar");
        productEntity.setStock(100.0);
        productEntity.setIdUser(1);

        return productEntity;
    }

    private ProductDtoResponse getProductDtoResponse(){
        ProductDtoResponse productDtoResponse = new ProductDtoResponse();
        productDtoResponse.setRef("MAD01");
        productDtoResponse.setName("Madar");
        productDtoResponse.setStock(100.0);
        productDtoResponse.setIdUser(1);

        return  productDtoResponse;
    }
}