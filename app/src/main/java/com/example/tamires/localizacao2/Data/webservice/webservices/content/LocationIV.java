package com.example.tamires.localizacao2.Data.webservice.webservices.content;

public class LocationIV {
    private double latitude;
    private  double longitude;

    public LocationIV(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {

        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {

        this.longitude = longitude;
    }

}