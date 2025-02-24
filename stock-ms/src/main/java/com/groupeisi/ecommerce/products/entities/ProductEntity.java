package com.groupeisi.ecommerce.products.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "products")
public class ProductEntity implements Serializable {

    @Id
    @Column(unique = true , nullable = false )
    private String ref;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double stock;

    @Column(nullable = false)
    private long idUser;

}
