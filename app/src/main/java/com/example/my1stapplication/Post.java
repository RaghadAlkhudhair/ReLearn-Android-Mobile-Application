package com.example.my1stapplication;

public class Post implements java.io.Serializable {
    String postID;
    String materialname;
    String coursename;
    String uniname;
    String materialtype;
    String price;
    String iban;
    String accountowner;
    String description;
    String phone,address,IBAN, username,bankname;

    public Post() {
    }

    public Post(String postID,String materialname, String coursename, String uniname,String materialtype, String price, String description  ) {
        this.postID = postID;
        this.materialname = materialname ;
        this.coursename = coursename;
        this.uniname = uniname;
        this.materialtype = materialtype;
        this.price = price;
        this.description=description;
    }

    public Post(String postID,String materialname, String coursename, String uniname,String materialtype, String price, String description ,String phone,String address,String IBAN, String username, String bankname ) {
        this.postID = postID;
        this.materialname = materialname ;
        this.coursename = coursename;
        this.uniname = uniname;
        this.materialtype = materialtype;
        this.price = price;
        this.description=description;
        this.phone = phone;
        this.address = address;
        this.IBAN=IBAN;
        this.username=username;
        this.bankname=bankname;
    }
    public String getPostID() {
        return postID;
    }

    public String getMaterialname() {
        return materialname;
    }

    public String getUniname() {
        return uniname;
    }
    public String getIban() {
        return iban;
    }

    public String getCoursename() {
        return coursename;
    }

    public String getPrice() {
        return price;
    }

    public String getMaterialtype() {
        return materialtype;
    }

    public String getDescription() {
        return description;
    }

    public String getAccountowner() {
        return accountowner;
    }

    public String getBankname() {
        return bankname;
    }
}
