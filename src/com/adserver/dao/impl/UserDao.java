package com.adserver.dao.impl;

import com.adserver.dao.AbstractBaseDao;
import com.adserver.dao.IUserDao;
import com.adserver.web.entity.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Gordon on 2014/4/15.
 */
public class UserDao extends AbstractBaseDao<User, Integer> implements IUserDao {

  /*  private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
*/
    @Override
    public void addUser(User user) {
//        Session session = sessionFactory.openSession();
//        session.save(user);
//        session.close();
        System.out.println("add user ----");
        sessionFactory.getCurrentSession().save(user);
        
    }

    @Override
    public List<User> getAllUser() {
        Query query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.list();
    }

    @Override
    public boolean updateUser(User user) {
        System.out.println("更新用户id： " + user.getId());
        String hql = "update User u set u.menus = ?,u.password=? where u.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, user.getMenus());
        query.setString(1, user.getPassword());
        query.setInteger(2, user.getId());
        return query.executeUpdate() > 0;
    }

    @Override
    public User getUser(String name) {
        String hql = "from User u where u.name=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, name);
        return (User) query.uniqueResult();
    }
    
    @Override
    public boolean delUser(int id) {
        String hql = "delete User u where u.id=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0, id);
        return query.executeUpdate() > 0;
    }
    
    
    
}
