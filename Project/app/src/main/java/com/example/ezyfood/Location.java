package com.example.ezyfood;

public class Location {
    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public Location(String branch_name, double latitude, double longitude) {
        this.branch_name = branch_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    String branch_name;
    double latitude;
    double longitude;

}
