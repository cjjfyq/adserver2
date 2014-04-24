package com.adserver.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.adserver.dao.AbstractBaseDao;
import com.adserver.dao.IMenuDao;
import com.adserver.web.entity.Menu;

/**
 * @author Gordon
 * 菜单实体类
 */
public class MenuDaoImpl extends AbstractBaseDao<Menu, Integer> implements IMenuDao {
    
//    private SessionFactory sessionFactory;
    
   /* 

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

    @Override
    public List<Menu> getAllMenu() {
        String hql = "from Menu";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<Menu> getAllParentMenu() {
        System.out.println("menu dao----------- ");
        String hql = "from Menu m where m.parentId=-1";
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public List<Menu> getSubMenuByParentMenu(Menu parentMenu) {
        String hql = "from Menu m where m.parentId=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0, parentMenu.getMenuId());
        return query.list();
    }
    

}
