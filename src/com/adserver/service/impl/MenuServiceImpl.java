package com.adserver.service.impl;

import java.util.List;

import com.adserver.dao.IMenuDao;
import com.adserver.service.IMenuService;
import com.adserver.web.entity.Menu;

public class MenuServiceImpl implements IMenuService {
    
    private IMenuDao menuDao;
    
    public IMenuDao getMenuDao() {
        return menuDao;
    }

    public void setMenuDao(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuDao.getAllMenu();
    }

    @Override
    public List<Menu> getAllParentMenu() {
        return menuDao.getAllParentMenu();
    }

    @Override
    public List<Menu> getSubMenuByParentMenu(Menu parentMenu) {
        return menuDao.getSubMenuByParentMenu(parentMenu);
    }
    
    

}
