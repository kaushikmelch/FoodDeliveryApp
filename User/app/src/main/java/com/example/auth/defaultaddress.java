package com.example.auth;

public class defaultaddress
{
    private double defaultlatitude;
    private double defaultlongitiude;

    public defaultaddress()
    {

    }

    public defaultaddress(double defaultlatitude, double defaultlongitiude) {
        this.defaultlatitude = defaultlatitude;
        this.defaultlongitiude = defaultlongitiude;
    }

    public double getDefaultlatitude() {
        return defaultlatitude;
    }

    public void setDefaultlatitude(double defaultlatitude) {
        this.defaultlatitude = defaultlatitude;
    }

    public double getDefaultlongitiude() {
        return defaultlongitiude;
    }

    public void setDefaultlongitiude(double defaultlongitiude) {
        this.defaultlongitiude = defaultlongitiude;
    }
}
