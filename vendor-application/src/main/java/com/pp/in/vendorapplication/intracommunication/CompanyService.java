package com.pp.in.vendorapplication.intracommunication;

import com.pp.in.vendorapplication.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


@FeignClient("company-application")
public interface CompanyService {

    @RequestMapping(value = "/company/hi", method = RequestMethod.GET)
    String hi();

    @RequestMapping(value = "/company/save/product",method = RequestMethod.POST)
     ResponseEntity<Map<String,Object>> saveProduct (@RequestBody Product product);

    @RequestMapping(value = "/company/get/product/{productId}",method = RequestMethod.GET)
      ResponseEntity<Map<String,Object>> getProduct (@PathVariable("productId") String productId);

    @RequestMapping(value = "/company/get/products",method = RequestMethod.GET)
    ResponseEntity<Map<String,Object>> getProducts ();


}
