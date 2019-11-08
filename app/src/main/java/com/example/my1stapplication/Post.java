package com.example.my1stapplication;

public class Post implements java.io.Serializable {
    String postID;
    String materialname;
    String coursename;
    String uniname;
    String materialtype;
    String price;
    String iban;

    String phone;
    String description;
    String address;
    String IBAN;
    String ownername;
    String bankname;

    public Post() {
    }

    public Post(String postID,String materialname, String coursename, String uniname,String materialtype, String price, String description, String phone, String address, String IBAN, String ownername, String bankname  ) {
        this.postID = postID;
        this.materialname = materialname ;
        this.coursename = coursename;
        this.uniname = uniname;
        this.materialtype = materialtype;
        this.price = price;

        this.description=description;
        this.address=address;
        this.phone=phone;
        this.IBAN=IBAN;
        this.ownername=ownername;
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
    //public String getIban() {
        //return iban;
    //}

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

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setMaterialtype(String materialtype) {
        this.materialtype = materialtype;
    }

    public void setUniname(String uniname) {
        this.uniname = uniname;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankname() {
        return bankname;
    }
}
