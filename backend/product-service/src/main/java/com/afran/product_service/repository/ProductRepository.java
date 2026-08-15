package com.afran.product_service.repository;

import com.afran.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByProductName(String productName);

    @Modifying
    @Query("""
            UPDATE Product p
            SET p.quantity = p.quantity - :quantity
            WHERE p.productId = :productId
            AND p.quantity >= :quantity
            """
    )
    int reserveQuantity(
            @Param("productId") UUID productId,
            @Param("quantity") Integer quantity
    );

}
