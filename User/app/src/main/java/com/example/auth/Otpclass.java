package com.example.auth;

public class Otpclass
{
    private String NAME,PHONE;
    private double DRIVER_LATITUDE,DRIVER_LONGITUDE;
    private boolean status;

    public Otpclass(String NAME, String PHONE, double DRIVER_LATITUDE, double DRIVER_LONGITUDE, boolean status) {
        this.NAME = NAME;
        this.PHONE = PHONE;
        this.DRIVER_LATITUDE = DRIVER_LATITUDE;
        this.DRIVER_LONGITUDE = DRIVER_LONGITUDE;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Otpclass() {
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

    public double getDRIVER_LATITUDE() {
        return DRIVER_LATITUDE;
    }

    public void setDRIVER_LATITUDE(double DRIVER_LATITUDE) {
        this.DRIVER_LATITUDE = DRIVER_LATITUDE;
    }

    public double getDRIVER_LONGITUDE() {
        return DRIVER_LONGITUDE;
    }

    public void setDRIVER_LONGITUDE(double DRIVER_LONGITUDE) {
        this.DRIVER_LONGITUDE = DRIVER_LONGITUDE;
    }
}
