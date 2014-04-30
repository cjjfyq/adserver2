package com.adserver.web.entity;

public interface IJsonParser {

    String getKey();
    void parseJson(String json);
    Object toJson();
    
}
