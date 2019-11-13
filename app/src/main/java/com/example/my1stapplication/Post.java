package com.example.my1stapplication;

public class Post implements java.io.Serializable {
    String postID,userID;
    private String materialname;
    private String coursename;
    private String uniname;
    private String materialtype;
    private String price;
    private String accountowner;
    private String description;
    private String phone,address,IBAN, username,bankname;

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

    public Post(String postID,String materialname, String coursename, String uniname,String materialtype, String price, String description ,String phone,String address,String IBAN, String username, String bankname ,String userID) {
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
        this.userID=userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getUniname() {
        return uniname;
    }

    public void setUniname(String uniname) {
        this.uniname = uniname;
    }

    public String getMaterialtype() {
        return materialtype;
    }

    public void setMaterialtype(String materialtype) {
        this.materialtype = materialtype;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getAccountowner() {
        return accountowner;
    }

    public void setAccountowner(String accountowner) {
        this.accountowner = accountowner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
}
