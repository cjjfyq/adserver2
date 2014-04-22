package com.adserver.controller;

import java.util.HashMap;
import java.util.Map;

import com.adserver.service.IUserService;
import com.adserver.util.MD5;
import com.adserver.web.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gordon on 2014/4/14.
 * 开发者账号直接在代码中：
 * 同时设置一个超级账号
 */
@Controller
public class LoginController {
    
    private static Map<String, User> kaifaUserMap;
    
    static {
        kaifaUserMap = new HashMap<String, User>();
        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        //是超级用户: 1
        user.setUserType(1);
        kaifaUserMap.put(user.getName(), user);
        
        //普通开发者：2
        user = new User();
        user.setName("cjj");
        user.setPassword("cjj");
        user.setUserType(2);
        kaifaUserMap.put(user.getName(), user);
    }

    @Resource(name = "userService")
    private IUserService managerService;
    
    /**
     * 登录不成功原因：用户名或密码不正确
     */
    private String loginResult;

    @RequestMapping("/login")
    public String showLoginPage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入 show login page---");
        
        //自动添入用户名跟密码
        User user = new User();
        user.setName("中文");
        user.setPassword("123");
        request.setAttribute("user", user);
        
        System.out.println("loginResult: " + loginResult);
       request.setAttribute("loginResult", loginResult);
        
        return "login/login";
    }

    @RequestMapping("/dologin")
    public String doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入 do login -----");
        System.out.println("user:" + user);
        
        User find = null;
        
        //检查是否开发者账号
        System.out.println("当前开发者账号有：" + kaifaUserMap);
        if (kaifaUserMap.containsKey(user.getName()) && kaifaUserMap.get(user.getName()).getPassword().equals(user.getPassword())) {
            find = kaifaUserMap.get(user.getName());
            System.out.println("是开发者账号: " + find.getUserType());
            return "login/success";
        }
        
        
        //依名查用户
        find = managerService.getUser(user.getName());
        
        if (find == null) {
            System.out.println("用户名不存在");
            loginResult = "用户名不存在";
            return "redirect:login";
        }
        
        //用户名存在 判断密码是否正确 保存md5加密
        if (!MD5.encode(user.getPassword()).equals(find.getPassword())) {
            System.out.println("密码不正确");
            loginResult = "密码不正确";
            return "redirect:login";
        }
        
        
        return "login/success";
    }



}
