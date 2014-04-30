package com.adserver.controller.mobile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adserver.dao.impl.DevicePropertyDao;
import com.adserver.service.impl.DevicePropertyService;
import com.adserver.web.entity.AdRequestInfo;
import com.adserver.web.entity.DeviceProperty;
import com.google.gson.Gson;

@Controller
public class MobileController {
    
    @Resource(name="devicePropertyService")
    private DevicePropertyService devicePropertyService;

    @RequestMapping("/test")
    public void test(DeviceProperty dp, AdRequestInfo requestInfo, HttpServletResponse response) {
        System.out.println("dp: " + dp);
        System.out.println("request info: " + requestInfo);
        PrintWriter writer = null;
        try {
            response.setContentType("application/json");
            writer = response.getWriter();
            String ret = "{\"result\":\"success\"}";
            writer.write(ret);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    @RequestMapping("/test2")
    public void test2(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        ServletInputStream is = null;
        try {
            String pathInfo = request.getPathInfo();
            System.out.println("path info: " + pathInfo);
            String queryString = request.getQueryString();
            System.out.println("query string: " + queryString);
            String contextPath = request.getContextPath();
            System.out.println("context path: " + contextPath);
            String servletPath = request.getServletPath();
            System.out.println("servlet path: " + servletPath);
            String requestURI = request.getRequestURI();
            System.out.println("request uri: " + requestURI);
            is = request.getInputStream();
            
            System.out.println(request.getContentLength());
            byte[] buf = new byte[request.getContentLength()];
            is.read(buf);
            String json = new String(buf, "utf-8");
            System.out.println(json);
            
            //解析json数据
            JSONObject obj = new JSONObject(json);
            DeviceProperty dp = new DeviceProperty();
            JSONObject objDp = obj.getJSONObject(dp.getKey());
            dp.parseJson(objDp.toString());
            System.out.println("dp: " + dp);
            devicePropertyService.addDeviceProperty(dp);
            
            
            AdRequestInfo requestInfo = new AdRequestInfo();
            requestInfo.parseJson(obj.getJSONObject(requestInfo.getKey()).toString());
            System.out.println("request info: " + requestInfo);
            
            response.setContentType("application/json");
            writer = response.getWriter();
            String ret = "{\"result\":\"success\"}";
            writer.write(ret);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
