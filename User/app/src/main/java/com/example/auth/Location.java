package com.example.auth;

import java.util.List;

public class Location
{
    private double latitude_pick;
    private double longitude_pick;
    private double latitude_drop;
    private double longitude_drop;
    private  long  timestamp;
    private  String status;
//    private int[] cart;
    private List<Integer> cart;

    public Location() {
    }



    public Location(List<Integer> cart, double latitude_pick, double longitude_pick, double latitude_drop, double longitude_drop , String status, long timestamp )
    {
        this.latitude_pick = latitude_pick;
        this.longitude_pick = longitude_pick;
        this.latitude_drop = latitude_drop;
        this.longitude_drop = longitude_drop;
        this.status = status;
        this.timestamp = timestamp;
        this.cart = cart;
    }

    public List<Integer> getCart() {
        return cart;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
