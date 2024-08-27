package com.example.rider;

import java.util.List;

public class transaction
{
    private double latitude_pick;
    private double longitude_pick;
    private double latitude_drop;
    private double longitude_drop;
    private String NAME,PHONE;
    private List<Integer> cart;
    private  String status;
    private  String totalcost;
    private  String OrderId;
    private  String UserId;
    private int counter;




    public transaction() {
    }

    public transaction(double latitude_pick, double longitude_pick, double latitude_drop, double longitude_drop, String NAME, String PHONE, List<Integer> cart, String status, String totalcost, String orderId, String userId, int counter) {
        this.latitude_pick = latitude_pick;
        this.longitude_pick = longitude_pick;
        this.latitude_drop = latitude_drop;
        this.longitude_drop = longitude_drop;
        this.NAME = NAME;
        this.PHONE = PHONE;
        this.cart = cart;
        this.status = status;
        this.totalcost = totalcost;
        OrderId = orderId;
        UserId = userId;
        this.counter = counter;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public List<Integer> getCart() {
        return cart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public void setCart(List<Integer> cart) {
        this.cart = cart;
    }

    public double getLatitude_pick() {
        return latitude_pick;
    }

    public void setLatitude_pick(double latitude_pick) {
        this.latitude_pick = latitude_pick;
    }

    public double getLongitude_pick() {
        return longitude_pick;
    }

    public void setLongitude_pick(double longitude_pick) {
        this.longitude_pick = longitude_pick;
    }

    public double getLatitude_drop() {
        return latitude_drop;
    }

    public void setLatitude_drop(double latitude_drop) {
        this.latitude_drop = latitude_drop;
    }

    public double getLongitude_drop() {
        return longitude_drop;
    }

    public void setLongitude_drop(double longitude_drop) {
        this.longitude_drop = longitude_drop;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }
}
