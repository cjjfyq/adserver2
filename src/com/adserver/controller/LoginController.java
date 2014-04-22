package com.adserver.controller;

import com.adserver.service.IUserService;
import com.adserver.web.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gordon on 2014/4/14.
 */
@Controller
public class LoginController {

    @Resource(name = "userService")
    private IUserService managerService;

    @RequestMapping("/login")
    public String showLoginPage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入 show login page---");
        return "login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(User manager) {
        System.out.println("进入 do login -----");
        System.out.println("manager:" + manager);
        return "mainpage";
    }



}
