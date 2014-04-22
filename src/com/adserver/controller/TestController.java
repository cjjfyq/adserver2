package com.adserver.controller;

import com.adserver.service.ITestSpring;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gordon on 2014/4/14.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource(name = "testSpring")
    private ITestSpring springManager;

    @RequestMapping("/showpage")
    public String showPage(HttpServletRequest request) {
        System.out.println("进入 showpage方法----");

        String ret = springManager.test();
        System.out.println("ret from spring: " + ret);

        return "test";
    }

}
