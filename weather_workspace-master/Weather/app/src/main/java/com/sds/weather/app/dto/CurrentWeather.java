package com.sds.weather.app.dto;

/**
 * Created on 2016-12-01.
 */

public class CurrentWeather {
    private String baseDate;
    private String baseTime;
    private int nx;
    private int ny;

    private float t1hValue;
    private float rn1Value;
    private float skyValue;
    private float uuuValue;
    private float vvvValue;
    private float rehValue;
    private float ptyValue;
    private float lgtValue;
    private float vecValue;
    private float wsdValue;

    public CurrentWeather() {
        // empty
    }

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public int getNy() {
        return ny;
    }

    public void setNy(int ny) {
        this.ny = ny;
    }

    public float getT1hValue() {
        return t1hValue;
    }

    public void setT1hValue(float t1hValue) {
        this.t1hValue = t1hValue;
    }

    public float getRn1Value() {
        return rn1Value;
    }

    public void setRn1Value(float rn1Value) {
        this.rn1Value = rn1Value;
    }

    public int getSkyValue() {
        return (int) skyValue;
    }

    public void setSkyValue(float skyValue) {
        this.skyValue = skyValue;
    }

    public float getUuuValue() {
        return uuuValue;
    }

    public void setUuuValue(float uuuValue) {
        this.uuuValue = uuuValue;
    }

    public float getVvvValue() {
        return vvvValue;
    }

    public void setVvvValue(float vvvValue) {
        this.vvvValue = vvvValue;
    }

    public float getRehValue() {
        return rehValue;
    }

    public void setRehValue(float rehValue) {
        this.rehValue = rehValue;
    }

    public int getPtyValue() {
        return (int) ptyValue;
    }

    public void setPtyValue(float ptyValue) {
        this.ptyValue = ptyValue;
    }

    public int getLgtValue() {
        return (int) lgtValue;
    }

    public void setLgtValue(float lgtValue) {
        this.lgtValue = lgtValue;
    }

    public float getVecValue() {
        return vecValue;
    }

    public void setVecValue(float vecValue) {
        this.vecValue = vecValue;
    }

    public float getWsdValue() {
        return wsdValue;
    }

    public void setWsdValue(float wsdValue) {
        this.wsdValue = wsdValue;
    }
}