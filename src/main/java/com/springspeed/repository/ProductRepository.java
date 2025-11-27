package com.springspeed.repository;

import com.springspeed.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Product Repository
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStatus(Integer status);

    List<Product> findByCategoryIdAndStatus(Long categoryId, Integer status);

    List<Product> findByBrandAndStatus(String brand, Integer status);

    @Query("SELECT p FROM Product p WHERE p.status = 1 AND p.isHot = true ORDER BY p.salesCount DESC")
    List<Product> findHotProducts();

    @Query("SELECT p FROM Product p WHERE p.status = 1 AND p.isNew = true ORDER BY p.createdTime DESC")
    List<Product> findNewProducts();

    @Query("SELECT p FROM Product p WHERE p.status = 1 ORDER BY p.salesCount DESC")
    List<Product> findPopularProducts();

    @Query("SELECT p FROM Product p WHERE p.status = 1 ORDER BY p.clickCount DESC")
    List<Product> findMostViewedProducts();

    @Query("SELECT p FROM Product p WHERE p.status = 1 AND (p.productName LIKE %:keyword% OR p.description LIKE %:keyword% OR p.tags LIKE %:keyword%)")
    List<Product> searchProducts(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.id IN :productIds AND p.status = 1")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);

    @Query("SELECT p FROM Product p WHERE p.status = 1 AND p.categoryId IN :categoryIds")
    List<Product> findProductsByCategoryIds(@Param("categoryIds") List<Long> categoryIds);

    @Query("SELECT p FROM Product p WHERE p.status = 1 AND p.tags LIKE %:tag%")
    List<Product> findProductsByTag(@Param("tag") String tag);
}
