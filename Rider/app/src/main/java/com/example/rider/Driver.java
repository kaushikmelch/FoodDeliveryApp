package com.example.rider;

public class Driver
{
    private String EMAIL,NAME,PHONE,PASSWORD,COMMUNITY,RADIUS;
    private int COUNTER;

    public Driver()
    {

    }

    public Driver(String COMMUNITY, int COUNTER , String EMAIL, String NAME,  String PASSWORD , String PHONE,String RADIUS) {
        this.EMAIL = EMAIL;
        this.NAME = NAME;
        this.PHONE = PHONE;
        this.PASSWORD = PASSWORD;
        this.COMMUNITY = COMMUNITY;
        this.RADIUS = RADIUS;
        this.COUNTER = COUNTER;
    }

    public String getRADIUS() {
        return RADIUS;
    }

    public void setRADIUS(String RADIUS) {
        this.RADIUS = RADIUS;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
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

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getCOMMUNITY() {
        return COMMUNITY;
    }

    public void setCOMMUNITY(String COMMUNITY) {
        this.COMMUNITY = COMMUNITY;
    }

    public int getCOUNTER() {
        return COUNTER;
    }

    public void setCOUNTER(int COUNTER) {
        this.COUNTER = COUNTER;
    }
}
