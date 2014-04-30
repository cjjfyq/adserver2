package com.adserver.web.entity;

import net.sf.json.JSONObject;

public class DeviceProperty implements IJsonParser {
    
    private int id;
    
    private String imsi;
    
    private String imei;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "DeviceProperty [id=" + id + ", imsi=" + imsi + ", imei=" + imei
                + "]";
    }

    @Override
    public String getKey() {
        return "a";
    }

    @Override
    public void parseJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            int startKey = 'a';
            System.out.println("parse json 开始key： " + getKey(startKey));
            imsi = obj.getString(getKey(startKey++));
            imei = obj.getString(getKey(startKey++));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String getKey(int key) {
        return String.valueOf((char)key);
    }

    @Override
    public Object toJson() {
        try {
            JSONObject obj = new JSONObject();
            int startKey = 'a';
            System.out.println("tojson 开始key： " + getKey(startKey));
            obj.put(getKey(startKey++), imsi);
            obj.put(getKey(startKey++), imei);
            return obj;
        } catch (Exception e) {
        }
        return null;
    }
    
    

}
