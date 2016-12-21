package com.sds.weather.app.dto;

/**
 * Created on 2016-11-30.
 */

public class Location {
    private int location_pk;
    private String sido;
    private String gugun;
    private String dong;
    private int x;
    private int y;

    public Location() {
        // empty
    }

    public Location(int location_pk, String sido, String gugun, String dong, int x, int y) {
        this.location_pk = location_pk;
        this.sido = sido;
        this.gugun = gugun;
        this.dong = dong;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "location_pk=" + location_pk +
                ", sido='" + sido + '\'' +
                ", gugun='" + gugun + '\'' +
                ", dong='" + dong + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public int getLocation_pk() {
        return location_pk;
    }

    public void setLocation_pk(int location_pk) {
        this.location_pk = location_pk;
    }

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getGugun() {
        return gugun;
    }

    public void setGugun(String gugun) {
        this.gugun = gugun;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
