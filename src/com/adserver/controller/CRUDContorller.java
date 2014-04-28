package com.adserver.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping("/adduser")
    public void addUser(User user, HttpServletResponse response) {
        System.out.println("进入 addUser-----" + user);
       /* if (user == null || user.getName() == null) {
            return "failed";
        }
        //判断用户是否已经存在
        User find = userService.getUser(user.getName());
        if (find != null) {
            System.out.println("用户名已经存在");
            return "failed";
        }
        System.out.println("user: " + user);
        userService.addUser(user);*/
//        return "redirect:listall";
        PrintWriter writer = null;
        try {
            userService.addUser(user);
            response.setContentType("application/json");
            writer = response.getWriter();
            User find = userService.getUser(user.getName());
            String json = null;
//            if (find != null) {
//                json = "{\"result\":\"failed\",\"error\":\"用户名已经存在\"}";
//                
//            } else {
                json = "{\"result\":\"success\",\"error\":\"\"}";
                userService.addUser(user);
//            }
//            String json = "{\"result\":\"failed\",\"error\":\"用户名已经存在\"}";
            writer.write(json);
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
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
        List<Menu> tmp = null;
        for (Menu menu : parentMenu) {
            //先添加父菜单
            tmp = new ArrayList<Menu>();
            tmp.add(menu);
            List<Menu> subMenus = menuDao.getSubMenuByParentMenu(menu);
            tmp.addAll(subMenus);
            menus.add(tmp);
        }
        request.setAttribute("menus", menus);
        return "login/userManager";
    }
    
    @RequestMapping("/namevalid")
    public void isNameValid(User user, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入 namevalide 方法---" + user);
        try {
           /* ServletInputStream is = request.getInputStream();
            byte[] buf = new byte[request.getContentLength()];
            System.out.println(request.getContentLength());
            is.read(buf);
            for (byte b : buf) {
                System.out.println(b);
            }
            System.out.println(new String(buf, "utf-8"));*/
            PrintWriter writer = response.getWriter();
            
            //以json格式的数据返回
            response.setContentType("application/json");
            User find = userService.getUser(user.getName());
            System.out.println("find: " + find);
            String json = null;
            if (find != null) {
                json = "{\"result\":\"failed\",\"error\":\"用户名已经存在\"}";
                
            } else {
                json = "{\"result\":\"success\",\"error\":\"\"}";
            }
//            String json = "{\"result\":\"success\",\"error\":\"\"}";
//            String json = "{\"result\":\"failed\",\"error\":\"用户名已经存在\"}";
            writer.write(json);
//            is.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/deluser")
    public void deleUser(int userId, HttpServletResponse response) {
        System.out.println("进入delUser方法----" + userId);
        PrintWriter writer = null;
        try {
            response.setContentType("application/json");
            writer = response.getWriter();
            boolean ret = userService.delUser(userId);
            System.out.println("删除结果： " + ret);
            String json = null;
            if (ret) {
                json = "{\"result\":\"success\",\"error\":\"\"}";
            } else {
                json = "{\"result\":\"failed\",\"error\":\"删除失败\"}";
            }
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
