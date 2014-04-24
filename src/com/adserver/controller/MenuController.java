package com.adserver.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adserver.dao.IMenuDao;
import com.adserver.service.IMenuService;
import com.adserver.web.entity.Menu;

/**
 * 菜单控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @Resource(name="menuService")
    private IMenuService menuService;
    
    

    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }


    public IMenuService getMenuService() {
        return menuService;
    }



    @RequestMapping("/allparentmenu")
    public String allParentMenu() {
        System.out.println("进入 all parent menu ----");
        List<Menu> allParentMenu = menuService.getAllParentMenu();
        System.out.println(allParentMenu);
        return "jsp/success";
    }

}
