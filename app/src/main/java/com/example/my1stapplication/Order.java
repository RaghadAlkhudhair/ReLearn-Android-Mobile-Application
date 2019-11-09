package com.example.my1stapplication;

import java.util.HashMap;

public class Order implements java.io.Serializable {

    String OrderID;
    String District;
    String StreetName;
    String BuyerID;
    String HouseNo;
    String TotalPrice;
    String Phone ;
    String Shipped;
    String rePaid;
    String TakenFromOwner;
    HashMap<String, Object> cart;

    public Order( String OrderID, String District, String StreetNumber, String BuyerID, String HouseNo, String TotalPrice, String Phone , String Shipped, String rePaid, String TakenFromOwner){
        this.BuyerID=BuyerID;
        this.OrderID=OrderID;
        this.District=District;
        this.HouseNo=HouseNo;
        this.Phone=Phone;
        this.TotalPrice=TotalPrice;
        this.StreetName=StreetNumber;
      this.Shipped=Shipped;
      this.rePaid=rePaid;
      this.TakenFromOwner=TakenFromOwner;

    }

    public Order(){}

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStreetNumber() {
        return StreetName;
    }

    public void setStreetNumber(String streetNumber) {
        StreetName = streetNumber;
    }

    public String getBuyerID() {
        return BuyerID;
    }

    public void setBuyerID(String buyerID) {
        BuyerID = buyerID;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public     String getShipped() {
        return Shipped;
    }

    public void setShipped(String shipped) {
        Shipped = shipped;
    }

    public     String getRePaid() {
        return rePaid;
    }

    public void setRePaid(    String rePaid) {
        this.rePaid = rePaid;
    }

    public     String getTakenFromOwner() {
        return TakenFromOwner;
    }

    public void setTakenFromOwner(    String takenFromOwner) {
        TakenFromOwner = takenFromOwner;
    }
}
