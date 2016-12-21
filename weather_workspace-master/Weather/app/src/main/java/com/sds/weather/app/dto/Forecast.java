package com.sds.weather.app.dto;

/**
 * Created by eee on 2016-11-29.
 */
public class Forecast {
    private String baseDate;
    private String baseTime;
    private int nx;
    private int ny;

    private String fcstDate;
    private String fcstTime;
    private float popValue;
    private float ptyValue;
    private float rehValue;
    private float skyValue;
    private float t3hValue;
    private float uuuValue;
    private float vecValue;
    private float vvvValue;
    private float wsdValue;
    private float r06Value;
    private float s06Value;
    private float tmnValue;
    private float tmxValue;
    private float wavValue;

    public Forecast() {

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

    public String getFcstDate() {
        return fcstDate;
    }

    public void setFcstDate(String fcstDate) {
        this.fcstDate = fcstDate;
    }

    public String getFcstTime() {
        return fcstTime;
    }

    public void setFcstTime(String fcstTime) {
        this.fcstTime = fcstTime;
    }

    public float getPopValue() {
        return popValue;
    }

    public void setPopValue(float popValue) {
        this.popValue = popValue;
    }

    public int getPtyValue() {
        return (int) ptyValue;
    }

    public void setPtyValue(float ptyValue) {
        this.ptyValue = ptyValue;
    }

    public float getRehValue() {
        return rehValue;
    }

    public void setRehValue(float rehValue) {
        this.rehValue = rehValue;
    }

    public int getSkyValue() {
        return (int) skyValue;
    }

    public void setSkyValue(float skyValue) {
        this.skyValue = skyValue;
    }

    public float getT3hValue() {
        return t3hValue;
    }

    public void setT3hValue(float t3hValue) {
        this.t3hValue = t3hValue;
    }

    public float getUuuValue() {
        return uuuValue;
    }

    public void setUuuValue(float uuuValue) {
        this.uuuValue = uuuValue;
    }

    public float getVecValue() {
        return vecValue;
    }

    public void setVecValue(float vecValue) {
        this.vecValue = vecValue;
    }

    public float getVvvValue() {
        return vvvValue;
    }

    public void setVvvValue(float vvvValue) {
        this.vvvValue = vvvValue;
    }

    public float getWsdValue() {
        return wsdValue;
    }

    public void setWsdValue(float wsdValue) {
        this.wsdValue = wsdValue;
    }

    public float getR06Value() {
        return r06Value;
    }

    public void setR06Value(float r06Value) {
        this.r06Value = r06Value;
    }

    public float getS06Value() {
        return s06Value;
    }

    public void setS06Value(float s06Value) {
        this.s06Value = s06Value;
    }

    public float getTmnValue() {
        return tmnValue;
    }

    public void setTmnValue(float tmnValue) {
        this.tmnValue = tmnValue;
    }

    public float getTmxValue() {
        return tmxValue;
    }

    public void setTmxValue(float tmxValue) {
        this.tmxValue = tmxValue;
    }

    public float getWavValue() {
        return wavValue;
    }

    public void setWavValue(float wavValue) {
        this.wavValue = wavValue;
    }
}
