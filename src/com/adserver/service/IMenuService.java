package com.adserver.service;

import java.util.List;

import com.adserver.web.entity.Menu;

public interface IMenuService {
    
    
    List<Menu> getAllMenu();
    
    List<Menu> getAllParentMenu();
    
    List<Menu> getSubMenuByParentMenu(Menu parentMenu);

}
