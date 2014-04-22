package com.adserver.controller;

import com.adserver.service.IUserService;
import com.adserver.web.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2014/4/16.
 * 对管理员管理： 进行增删改查
 *  
 */
@Controller
@RequestMapping("/user")
public class CRUDContorller {

    @Resource(name = "userService")
    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/addUser")
    public String addUser(User user) {
        System.out.println("进入 addUser-----");
        System.out.println("user: " + user);
        userService.addUser(user);
        return "success";
    }
    
    /**
     * 显示所有管理员
     * @return
     */
    @RequestMapping("/listall")
    public String showAllUser() {
        System.out.println();
        return "jsp/userManager";
    }

}
