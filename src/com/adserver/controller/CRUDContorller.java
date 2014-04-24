package com.adserver.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adserver.dao.IMenuDao;
import com.adserver.service.IUserService;
import com.adserver.web.entity.Menu;
import com.adserver.web.entity.User;

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
    
    @Resource(name="menuDao")
    private IMenuDao menuDao;

    
    
    public void setMenuDao(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/addUser")
    public String addUser(User user) {
        System.out.println("进入 addUser-----" + user);
        if (user == null || user.getName() == null) {
            return "failed";
        }
        //判断用户是否已经存在
        User find = userService.getUser(user.getName());
        if (find != null) {
            System.out.println("用户名已经存在");
            return "failed";
        }
        System.out.println("user: " + user);
        userService.addUser(user);
        return "redirect:listall";
    }
    
    /**
     * 显示所有管理员
     * @return
     */
    @RequestMapping("/listall")
    public String showAllUser(HttpServletRequest request) {
        System.out.println("进入 方法 show all user---");
        List<User> users = userService.getAllUser();
        request.setAttribute("users", users);
        List<List<Menu>> menus = new ArrayList<List<Menu>>();
        //所有父菜单
        List<Menu> parentMenu = menuDao.getAllParentMenu();
        for (Menu menu : parentMenu) {
            List<Menu> subMenus = menuDao.getSubMenuByParentMenu(menu);
            menus.add(subMenus);
        }
        request.setAttribute("menus", menus);
        return "login/userManager";
    }

}
