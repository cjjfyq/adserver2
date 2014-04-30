package com.adserver.web.entity;

import net.sf.json.JSONObject;

public class AdRequestInfo implements IJsonParser {

    private int requestType;

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "AdRequestInfo [requestType=" + requestType + "]";
    }

    @Override
    public String getKey() {
        return "b";
    }

    @Override
    public void parseJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            requestType = obj.getInt("a");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object toJson() {
        return null;
    }
    
    
}
