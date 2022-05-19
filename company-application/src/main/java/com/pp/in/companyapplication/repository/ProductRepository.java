package com.pp.in.companyapplication.repository;

import com.pp.in.companyapplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product,Long> {


    Product findByProductId(String productId);
}
