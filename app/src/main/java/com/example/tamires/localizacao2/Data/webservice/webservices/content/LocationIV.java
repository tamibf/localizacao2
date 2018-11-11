package com.example.tamires.localizacao2.Data.webservice.webservices.content;

public class LocationIV {
    private StringValue latitude;
    private  StringValue longitude;

    public LocationIV(StringValue latitude, StringValue longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StringValue getLatitude() {
        return latitude;
    }

    public void setLatitude(StringValue latitude) {
        this.latitude = latitude;
    }

    public StringValue getLongitude() {
        return longitude;
    }

    public void setLongitude(StringValue longitude) {
        this.longitude = longitude;
    }
}