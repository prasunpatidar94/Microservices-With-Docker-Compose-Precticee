package com.pp.in.companyapplication.service;

import com.pp.in.companyapplication.entity.Product;
import com.pp.in.companyapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private ProductRepository productRepository;

    public Map<String, Object> saveProduct(Product product) {

        Map<String,Object> map = new HashMap<>();
        Product productSave = productRepository.save(product);
        map.put("code",1000);
        map.put("data",productSave);
        return map;

    }

    public Map<String, Object> getProduct(String productId) {

        Map<String,Object> map = new HashMap<>();
        Product product = productRepository.findByProductId(productId);
        map.put("code",1000);
        map.put("data",product);
        return map;
    }

    public Map<String, Object> getProducts() {

        Map<String,Object> map = new HashMap<>();
      List<Product> products = productRepository.findAll();
        map.put("code",1000);
        map.put("data",products);
        return map;
    }
}
