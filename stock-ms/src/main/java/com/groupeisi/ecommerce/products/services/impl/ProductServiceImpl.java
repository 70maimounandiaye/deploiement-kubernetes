package com.groupeisi.ecommerce.products.services.impl;

import com.groupeisi.ecommerce.products.dto.requests.ProductDtoRequest;
import com.groupeisi.ecommerce.products.dto.responses.ProductDtoResponse;
import com.groupeisi.ecommerce.products.entities.ProductEntity;
import com.groupeisi.ecommerce.exception.EntityExistsException;
import com.groupeisi.ecommerce.exception.EntityNotFoundException;
import com.groupeisi.ecommerce.products.mapper.ProductsMapper;
import com.groupeisi.ecommerce.products.repository.ProductRepository;
import com.groupeisi.ecommerce.products.services.ProductService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Getter
@Setter
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOTFOUND = "product.notfound";
    private ProductRepository productRepository;
    private ProductsMapper productsMapper;
    private MessageSource messageSource;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    @Transactional
    public Optional<ProductDtoResponse> saveProduct(ProductDtoRequest productDto) {

        if (productRepository.findByRef(productDto.getRef()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("product.exists", new Object[]{productDto.getRef()}, Locale.getDefault()));
        }
        ProductEntity product = productsMapper.toProductEntity(productDto);
        logger.info("Reference: {}", product);
        var productEntity = productRepository.save(product);
        var productDtoResponse = productsMapper.toProductDtoResponse(productEntity);
        return Optional.of(productDtoResponse);

    }

    @Override
    public List<ProductDtoResponse> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return productsMapper.toProductDtoResponseList(products);
    }

    @Override
    public Optional<ProductDtoResponse> getProductByRef(String ref) {
        return productRepository.findByRef(ref)
                .map(product -> Optional.of(productsMapper.toProductDtoResponse(product)))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(PRODUCT_NOTFOUND, new Object[]{ref}, Locale.getDefault())));
    }

    @Override
    public boolean deleteProduct(String ref) {
        if (productRepository.findByRef(ref).isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage(PRODUCT_NOTFOUND, new Object[]{ref}, Locale.getDefault()));
        }
        productRepository.deleteById(ref);
        return true;
    }

    @Override
    public Optional<ProductDtoResponse> updateProduct(String ref, ProductDtoRequest productDto) {
        return productRepository.findByRef(ref)
                .map(product -> {
                    product.setRef(productDto.getRef());
                    product.setName(productDto.getName());
                    product.setStock(productDto.getStock());
                    product.setIdUser(productDto.getIdUser());
                   var productEntity = productRepository.save(product);
                   return Optional.of(productsMapper.toProductDtoResponse(productEntity));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(PRODUCT_NOTFOUND, new Object[]{productDto.getRef()}, Locale.getDefault())));
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setProductsMapper(ProductsMapper productsMapper) {
        this.productsMapper = productsMapper;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
