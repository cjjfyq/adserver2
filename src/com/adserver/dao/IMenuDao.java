package com.adserver.dao;

import java.util.List;

import com.adserver.web.entity.Menu;

public interface IMenuDao {
    
    List<Menu> getAllMenu();
    
    List<Menu> getAllParentMenu();
    
    List<Menu> getSubMenuByParentMenu(Menu parentMenu);

}
