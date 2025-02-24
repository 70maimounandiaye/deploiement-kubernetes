package com.groupeisi.ecommerce.products.repository;

import com.groupeisi.ecommerce.products.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    Optional<ProductEntity> findByRef(String ref);
    Optional<ProductEntity> findByName(String name);
}
