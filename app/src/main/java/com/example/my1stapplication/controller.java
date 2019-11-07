package com.example.my1stapplication;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
        import android.app.Application;

public class controller extends Application{

    private  ArrayList<ModelProducts> myProducts = new ArrayList<ModelProducts>();
    private  ModleCart   myCart = new ModleCart();


    public ModelProducts getProducts(int pPosition) {

        return myProducts.get(pPosition);
    }

    public void setProducts(ModelProducts Products) {

        myProducts.add(Products);

    }

    public ModleCart getCart() {

        return myCart;

    }

    public int getProductsArraylistSize() {

        return myProducts.size();
    }

}

