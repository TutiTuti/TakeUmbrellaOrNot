package com.example.teamproject;

public class getLocation {
    private double GPSLat;
    private double GPSLon;
    private String state;

    public getLocation(double lat, double lon, String location){
        this.GPSLat = lat;
        this.GPSLon = lon;
        this.state = location;
    }

    public double GpsLat(){
        return GPSLat;
    }

    public double GpsLon(){
        return GPSLon;
    }

    public String State(){
        return state;
    }
}
