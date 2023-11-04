package com.pawlowskisolutions.ecommerce.dao;

import com.pawlowskisolutions.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


// Accept call for specified origin
@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
    // SELECT * FROM product where category_id=?
    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);
    // SELECT * FROM Product p WHERE p.name LIKE CONCAT('%', :name , '%')
    Page<Product> findByNameContaining(@Param("name") String name, Pageable page);
}