package com.example.my1stapplication;

import java.io.Serializable;

public class ModelProducts implements Serializable {

    private String coursename ;
    private String price;

    public ModelProducts(String coursename,String price)
    {
        this.coursename  = coursename;
        this.price = price;
    }

    public String getProductName() {

        return coursename;
    }

    public String getProductPrice() {

        return price;
    }

}
