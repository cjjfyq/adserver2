package com.adserver.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    
    
    public static String encode(String str) {
        if(str == null) {
            str = "";
        }
        
        StringBuilder sb = new StringBuilder();
        byte[] result = str.getBytes();
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            md5.update(result);
            
            for (byte by : md5.digest()) {
                sb.append(String.format("%02X", by));//将生成的字节MD5值转换成字符串
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return sb.toString();
    }

}
