package com.pp.in.companyapplication.controller;

import com.pp.in.companyapplication.entity.Product;
import com.pp.in.companyapplication.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private Service service;

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String hi (){
        return "This is company application";
    }


    @RequestMapping(value = "/save/product",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> saveProduct (@RequestBody Product product){

        Map<String,Object> res = service.saveProduct(product);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/get/product/{productId}",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProduct (@PathVariable("productId") String productId){

        Map<String,Object> res = service.getProduct(productId);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/get/products",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProducts (){

        Map<String,Object> res = service.getProducts();
        return ResponseEntity.ok(res);
    }


}
