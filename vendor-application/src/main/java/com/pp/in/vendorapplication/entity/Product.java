package com.pp.in.vendorapplication.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
    public class Product {

        private Long id;
        private String productId;
        private String productName;
        private String brand;
    }

