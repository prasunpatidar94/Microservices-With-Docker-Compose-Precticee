package com.pp.in.vendorapplication.controller;

import com.pp.in.vendorapplication.intracommunication.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String hi (){
        return "This is Vendor application";
    }

    @RequestMapping(value = "/com/hi",method = RequestMethod.GET)
    public String comHi (){
        return companyService.hi();
    }


    @RequestMapping(value = "/get/product/{productId}",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProduct (@PathVariable("productId") String productId){

     Map<String,Object> res =  companyService.getProduct(productId).getBody();
        return  ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/get/products/",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProducts (){

        Map<String,Object> res =  companyService.getProducts().getBody();
        return  ResponseEntity.ok(res);
    }
}
