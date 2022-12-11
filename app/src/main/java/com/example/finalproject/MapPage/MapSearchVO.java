package com.example.finalproject.MapPage;

public class MapSearchVO {
    private String name;
    private String road_address;
    private String location_address;
    private double x;
    private double y;
    private String tel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoad_address() {
        return road_address;
    }

    public void setRoad_address(String road_address) {
        this.road_address = road_address;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "MapSearchVO{" +
                "name='" + name + '\'' +
                ", road_address='" + road_address + '\'' +
                ", location_address='" + location_address + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", tel='" + tel + '\'' +
                '}';
    }
}
